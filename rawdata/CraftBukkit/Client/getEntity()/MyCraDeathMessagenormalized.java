/**
 * プレイヤーが死亡したときに呼び出されるメソッド
 *
 * @param event
 * 		プレイヤー死亡イベント
 */
@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.HIGHEST, ignoreCancelled = true)
public void onPlayerDeath(org.bukkit.event.entity.PlayerDeathEvent event) {
    org.bukkit.event.entity.PlayerDeathEvent _CVAR0 = event;
    // プレイヤーとプレイヤーが最後に受けたダメージイベントを取得
    org.bukkit.entity.Player deader = _CVAR0.getEntity();
    org.bukkit.event.entity.PlayerDeathEvent _CVAR1 = event;
    org.bukkit.entity.Player _CVAR2 = _CVAR1.getEntity();
    final org.bukkit.event.entity.EntityDamageEvent cause = _CVAR2.getLastDamageCause();
    // 死亡メッセージ
    java.lang.String deathMessage = event.getDeathMessage();
    // ダメージイベントを受けずに死んだ 死因不明
    if (cause == null) {
        deathMessage = getMessage("unknown");// Unknown

    } else // ダメージイベントがEntityDamageByEntityEvent(エンティティが原因のダメージイベント)かどうかチェック
    if (cause instanceof org.bukkit.event.entity.EntityDamageByEntityEvent) {
        // EntityDamageByEventのgetDamagerメソッドから原因となったエンティティを取得
        org.bukkit.entity.Entity killer = ((org.bukkit.event.entity.EntityDamageByEntityEvent) (cause)).getDamager();
        // エンティティの型チェック 特殊な表示の仕方が必要
        if (killer instanceof org.bukkit.entity.Player) {
            // 倒したプレイヤー名取得
            org.bukkit.entity.Player killerP = ((org.bukkit.entity.Player) (killer));
            // killerが持ってたアイテム
            org.bukkit.inventory.ItemStack hand = killerP.getItemInHand();
            java.lang.String handItemName = hand.getType().toString();
            if (hand.getType().equals(org.bukkit.Material.AIR)) {
                handItemName = "素手";
            }
            deathMessage = getMessage("pvp");
            deathMessage = deathMessage.replace("%k", killerP.getName());
            deathMessage = deathMessage.replace("%i", handItemName);
        } else if ((killer instanceof org.bukkit.entity.Wolf) && ((org.bukkit.entity.Wolf) (killer)).isTamed()) {
            // 飼い主取得
            java.lang.String tamer = ((org.bukkit.entity.Wolf) (killer)).getOwner().getName();
            deathMessage = getDeathMessageByMob("tamewolf", ((org.bukkit.entity.Wolf) (killer)));
            deathMessage = deathMessage.replace("%o", tamer);
        } else if (killer instanceof org.bukkit.entity.Arrow) {
            org.bukkit.entity.Arrow arrow = ((org.bukkit.entity.Arrow) (killer));
            org.bukkit.entity.LivingEntity shooter = arrow.getShooter();
            if (shooter instanceof org.bukkit.entity.Player) {
                java.lang.String killerName = ((org.bukkit.entity.Player) (shooter)).getName();
                deathMessage = getMessage("arrow");
                deathMessage = deathMessage.replace("%k", killerName);
            } else if (shooter instanceof org.bukkit.entity.Skeleton) {
                deathMessage = getDeathMessageByMob("skeleton", ((org.bukkit.entity.Skeleton) (shooter)));
            } else {
                deathMessage = getMessage("dispenser");
            }
        } else if (killer instanceof org.bukkit.entity.EnderPearl) {
            deathMessage = getMessage("enderpearl");
        } else if (killer instanceof org.bukkit.entity.Projectile) {
            // 投げたプレイヤー取得
            org.bukkit.entity.LivingEntity shooter = ((org.bukkit.entity.Projectile) (killer)).getShooter();
            if (shooter instanceof org.bukkit.entity.Player) {
                java.lang.String shooterName = ((org.bukkit.entity.Player) (shooter)).getName();
                if (killer instanceof org.bukkit.entity.ThrownPotion) {
                    deathMessage = getMessage("potion");
                } else {
                    deathMessage = getMessage("throw");
                }
                deathMessage = deathMessage.replace("%k", shooterName);
            } else if (shooter instanceof org.bukkit.entity.Witch) {
                deathMessage = getDeathMessageByMob("witch", shooter);
            }
        } else // 直接 getMessage メソッドを呼ぶ
        if (killer instanceof org.bukkit.entity.LivingEntity) {
            deathMessage = getDeathMessageByMob(killer.getType().getName().toLowerCase(), ((org.bukkit.entity.LivingEntity) (killer)));
        } else {
            deathMessage = getMessage(killer.getType().getName().toLowerCase());
        }
    } else if (cause.getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.FIRE_TICK) {
        deathMessage = getMessage("fire");
    } else {
        deathMessage = getMessage(cause.getCause().toString().toLowerCase());
    }
    // %p を、死亡した人の名前で置き換えする
    deathMessage = deathMessage.replace("%p", deader.getName());
    // カラーコードを置き換える
    deathMessage = com.github.ucchyocean.mdm.Utility.replaceColorCode(deathMessage);
    if (prefixWorld) {
        // ワールド名を頭につける
        org.bukkit.World world = deader.getWorld();
        deathMessage = (("[" + world.getName()) + "] ") + deathMessage;
    }
    if (loggingDeathMessage) {
        // ロギング
        getLogger().info(org.bukkit.ChatColor.stripColor(deathMessage));
    }
    if (suppressDeathMessage) {
        // メッセージを消去して、OPにだけ送信する
        event.setDeathMessage("");
        org.bukkit.entity.Player[] players = getServer().getOnlinePlayers();
        for (org.bukkit.entity.Player player : players) {
            if (player.isOp()) {
                player.sendMessage(deathMessage);
            }
        }
    } else {
        // メッセージを再設定する
        event.setDeathMessage(deathMessage);
    }
}