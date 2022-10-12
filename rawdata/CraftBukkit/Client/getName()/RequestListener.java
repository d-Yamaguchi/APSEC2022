package me.josvth.trade.request;

import me.josvth.bukkitformatlibrary.FormattedMessage;
import me.josvth.bukkitformatlibrary.managers.FormatManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class RequestListener implements Listener {

	private final RequestManager requestManager;
   	private final FormatManager formatManager;

	public RequestListener(RequestManager requestManager, FormatManager formatManager) {
		this.requestManager = requestManager;
		this.formatManager = formatManager;
	}

	@EventHandler
	public void onRightClick(PlayerInteractEntityEvent event) {

		if (!(event.getRightClicked() instanceof Player)) return;

		Player requester = event.getPlayer();
		Player requested = (Player) event.getRightClicked();

		RequestMethod method;

		if (requester.isSneaking())
			method = RequestMethod.SHIFT_RIGHT_CLICK;
		else
			method = RequestMethod.RIGHT_CLICK;

		handleEvent(event, requester, requested, method);

	}

	@EventHandler
	public void onLeftClick(EntityDamageByEntityEvent event) {

		if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;

		Player requester = (Player) event.getDamager();
		Player requested = (Player) event.getEntity();

		RequestMethod method;

		if (requester.isSneaking())
			method = RequestMethod.SHIFT_LEFT_CLICK;
		else
			method = RequestMethod.LEFT_CLICK;

		handleEvent(event, requester, requested, method);

	}

	private void handleEvent(Cancellable event, Player requester, Player requested, RequestMethod method) {

		// First we check if the requester his answering a request
		Request request = requestManager.getRequest(requester.getName(), requested.getName());

		if (request != null) {

			if (requestManager.mayUseMethod(requester, method)) {
				event.setCancelled(true);

				RequestRestriction restriction = requestManager.accept(request);

				if (restriction == RequestRestriction.ALLOW) {
					FormattedMessage message = formatManager.getMessage("trading.started");
					message.send(requester, "%player%", requested.getName());
					message.send(requested, "%player%", requester.getName());
				} else {
					FormattedMessage message = formatManager.getMessage(restriction.tradeMessagePath);
					message.send(requester, "%player%", requested.getName());
					message.send(requested, "%player%", requester.getName());
				}

			}


		} else {

			request = new Request(requester.getName(), requested.getName(), method);

			RequestRestriction restriction = requestManager.checkRequest(request);

			if (restriction == RequestRestriction.ALLOW) {
				requestManager.submit(request);
				event.setCancelled(true);
			}

			if (restriction != RequestRestriction.METHOD && restriction != RequestRestriction.PERMISSION) {
				FormattedMessage message = formatManager.getMessage(restriction.requestMessagePath);
				message.send(requester, "%player%", requested.getName());
			}

		}

	}
}
