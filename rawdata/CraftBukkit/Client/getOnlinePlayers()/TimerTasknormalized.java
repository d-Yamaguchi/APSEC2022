/**
 * カウントダウンの音を出す
 */
private void playCountdownSound() {
    java.lang.String name = configData.getPlaySoundCountdown();
    org.bukkit.Sound sound;
    if (isValidSoundName(name)) {
        sound = org.bukkit.Sound.valueOf(name);
    } else {
        sound = org.bukkit.Sound.NOTE_STICKS;
    }
    if (configData.isAnnounceToOnlyTeamMembers() && (com.github.ucchyocean.et.ExpTimer.getInstance().getColorTeaming() != null)) {
        java.util.HashMap<java.lang.String, java.util.ArrayList<org.bukkit.entity.Player>> members = com.github.ucchyocean.et.ExpTimer.getInstance().getColorTeaming().getTeamMembers();
        for (java.util.ArrayList<org.bukkit.entity.Player> players : members.values()) {
            if (players != null) {
                for (org.bukkit.entity.Player player : players) {
                    player.playSound(player.getEyeLocation(), sound, 1.0F, 1.0F);
                }
            }
        }
    } else {
        org.bukkit.entity.Player[] _CVAR0 = org.bukkit.Bukkit.getOnlinePlayers();
        for (org.bukkit.entity.Player player : ) {
            player.playSound(player.getEyeLocation(), sound, 1.0F, 1.0F);
        }
    }
}