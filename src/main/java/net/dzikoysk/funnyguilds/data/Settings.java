package net.dzikoysk.funnyguilds.data;

import net.dzikoysk.funnyguilds.FunnyGuilds;
import net.dzikoysk.funnyguilds.FunnyLog;
import net.dzikoysk.funnyguilds.util.Parser;
import net.dzikoysk.funnyguilds.util.StringUtils;
import net.dzikoysk.funnyguilds.util.element.PlayerListManager;
import net.dzikoysk.funnyguilds.util.element.PlayerListScheme;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.panda_lang.panda.util.configuration.PandaConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Settings {

    private static final File SETTINGS = new File(FunnyGuilds.getInstance().getDataFolder(), "config.yml");
    private static final String VERSION = "4.0";

    private static Settings instance;
    public String pluginName;
    public int createNameLength;
    public int createNameMinLength;
    public int createTagLength;
    public int createTagMinLength;
    public List<ItemStack> createItems;
    public List<ItemStack> createItemsVip;
    public int createDistance;
    public Material createMaterial;
    public String createStringMaterial;
    public int createCenterY;
    public boolean createCenterSphere;
    public int regionSize;
    public int regionMaxSize;
    public int regionMinDistance;
    public int regionNotificationTime;
    public int regionNotificationCooldown;
    public int regionExplode;
    public List<String> regionCommands;
    public boolean eventMove;
    public boolean eventPhysics;
    public boolean enlargeEnable;
    public int enlargeSize;
    public List<ItemStack> enlargeItems;
    public int warLives;
    public long warProtection;
    public long warWait;
    public long validityStart;
    public long validityTime;
    public long validityWhen;
    public List<ItemStack> validityItems;
    public int inviteMembers;
    public List<ItemStack> joinItems;
    public boolean infoPlayerSneaking;
    public String prefixOur;
    public String prefixAllies;
    public String prefixEnemies;
    public String prefixOther;
    public boolean dummyEnable;
    public String dummySuffix;
    public String chatGuild;
    public String chatRank;
    public String chatPoints;
    public String chatPriv;
    public String chatAlly;
    public String chatGlobal;
    public String chatPrivDesign;
    public String chatAllyDesign;
    public String chatGlobalDesign;
    public int rankStart;
    public double rankPercent;
    public boolean damageGuild;
    public boolean damageAlly;
    public boolean baseEnable;
    public int baseDelay;
    public List<ItemStack> baseItems;
    public int explodeRadius;
    public HashMap<Material, Double> explodeMaterials;
    public boolean playerlistEnable;
    public int playerlistInterval;
    public int playerlistPing;
    public boolean playerlistPatch;
    public String playerlistPoints;
    public String excCreate;
    public String excDelete;
    public String excConfirm;
    public String excInvite;
    public String excJoin;
    public String excLeave;
    public String excKick;
    public String excBase;
    public String excEnlarge;
    public String excGuild;
    public String excAlly;
    public String excBreak;
    public String excInfo;
    public String excPlayer;
    public String excTop;
    public String excValidity;
    public String excLeader;
    public String excDeputy;
    public String excRanking;
    public String mxcBase;
    public String mxcPvP;
    public List<String> excCreateAliases;
    public List<String> excDeleteAliases;
    public List<String> excConfirmAliases;
    public List<String> excInviteAliases;
    public List<String> excJoinAliases;
    public List<String> excLeaveAliases;
    public List<String> excKickAliases;
    public List<String> excBaseAliases;
    public List<String> excEnlargeAliases;
    public List<String> excGuildAliases;
    public List<String> excAllyAliases;
    public List<String> excBreakAliases;
    public List<String> excInfoAliases;
    public List<String> excPlayerAliases;
    public List<String> excTopAliases;
    public List<String> excValidityAliases;
    public List<String> excLeaderAliases;
    public List<String> excDeputyAliases;
    public List<String> excRankingAliases;
    public List<String> mxcBaseAliases;
    public List<String> mxcPvPAliases;
    public String axcMain;
    public String axcAdd;
    public String axcDelete;
    public String axcKick;
    public String axcTeleport;
    public String axcPoints;
    public String axcKills;
    public String axcDeaths;
    public String axcBan;
    public String axcUnban;
    public String axcLives;
    public String axcMove;
    public String axcValidity;
    public String axcName;
    public int dataInterval;
    public boolean flat;
    public boolean mysql;
    public String mysqlHostname;
    public String mysqlPort;
    public String mysqlDatabase;
    public String mysqlUser;
    public String mysqlPassword;
    private PandaConfiguration pc;

    public Settings() {
        Manager.loadDefaultFiles(new String[] {"config.yml"});
        update();
        load();
    }

    public static Settings getInstance() {
        if (instance == null)
            instance = new Settings();
        return instance;
    }

    private boolean update() {
        this.pc = new PandaConfiguration(SETTINGS);
        String version = pc.getString("version");
        if (version != null && version.equals(Settings.VERSION))
            return true;
        FunnyLog.info("Updating the plugin settings ...");
        SETTINGS.renameTo(new File(FunnyGuilds.getInstance().getDataFolder(), "config.old"));
        Manager.loadDefaultFiles(new String[] {"config.yml"});
        pc = new PandaConfiguration(SETTINGS);
        FunnyLog.info("Successfully updated settings!");
        return true;
    }

    private void load() {

        // Plugin Section
        this.pluginName = "FunnyGuilds";
        String name = pc.getString("plugin-name");
        if (name != null && !name.isEmpty())
            this.pluginName = name;

        // Create Section
        this.createNameLength = pc.getInt("name-length");
        this.createTagLength = pc.getInt("tag-length");
        this.createNameMinLength = pc.getInt("name-min-length");
        this.createTagMinLength = pc.getInt("tag-min-length");

        List<String> list = pc.getStringList("items");
        List<ItemStack> items = new ArrayList<ItemStack>();
        for (String item : list) {
            if (item == null)
                continue;
            ItemStack itemstack = Parser.parseItem(item);
            if (itemstack != null)
                items.add(itemstack);
        }
        this.createItems = items;

        list = pc.getStringList("items-vip");
        items = new ArrayList<ItemStack>();
        for (String item : list) {
            ItemStack itemstack = Parser.parseItem(item);
            if (itemstack != null)
                items.add(itemstack);
        }
        this.createItemsVip = items;
        this.createDistance = pc.getInt("create-distance");
        this.createStringMaterial = pc.getString("create-material");
        this.createMaterial = Parser.parseMaterial(createStringMaterial);
        this.createCenterY = pc.getInt("create-center-y");
        this.createCenterSphere = pc.getBoolean("create-center-sphere");

        // Region Section
        this.regionSize = pc.getInt("region-size");
        this.regionMinDistance = pc.getInt("region-min-distance");
        this.regionNotificationTime = pc.getInt("region-notification-time");
        this.regionNotificationCooldown = pc.getInt("region-notification-cooldown");
        this.regionExplode = pc.getInt("region-explode");
        this.regionCommands = pc.getStringList("region-commands");

        // Event Section
        if (this.createMaterial != null && this.createMaterial == Material.DRAGON_EGG)
            this.eventPhysics = true;
        this.eventMove = pc.getBoolean("event-move");

        // Enlarge Section
        this.enlargeEnable = pc.getBoolean("enlarge-enable");
        if (this.enlargeEnable) {
            this.enlargeSize = pc.getInt("enlarge-size");

            list = pc.getStringList("enlarge-items");
            items = new ArrayList<ItemStack>();
            for (String item : list) {
                ItemStack itemstack = Parser.parseItem(item);
                if (itemstack != null)
                    items.add(itemstack);
            }
            this.enlargeItems = items;
        }

        // War section
        this.warLives = pc.getInt("war-lives");
        this.warProtection = Parser.parseTime(pc.getString("war-protection"));
        this.warWait = Parser.parseTime(pc.getString("war-wait"));

        // Validity Section
        this.validityStart = Parser.parseTime(pc.getString("validity-start"));
        this.validityTime = Parser.parseTime(pc.getString("validity-time"));
        this.validityWhen = Parser.parseTime(pc.getString("validity-when"));
        list = pc.getStringList("validity-items");
        items = new ArrayList<ItemStack>();
        for (String item : list) {
            ItemStack itemstack = Parser.parseItem(item);
            if (itemstack != null)
                items.add(itemstack);
        }
        this.validityItems = items;

        // Other Section
        this.inviteMembers = pc.getInt("max-members");
        this.infoPlayerSneaking = pc.getBoolean("info-player-sneaking");
        items = new ArrayList<ItemStack>();
        for (String item : pc.getStringList("join-items")) {
            ItemStack itemstack = Parser.parseItem(item);
            if (itemstack != null)
                items.add(itemstack);
        }
        this.joinItems = items;

        // Prefix Section
        this.prefixOur = StringUtils.colored(pc.getString("prefix-our"));
        this.prefixAllies = StringUtils.colored(pc.getString("prefix-allies"));
        this.prefixOther = StringUtils.colored(pc.getString("prefix-other"));

        // Dummy Section
        this.dummyEnable = pc.getBoolean("dummy-enable");
        this.dummySuffix = StringUtils.colored(pc.getString("dummy-suffix"));

        // Chat Section
        this.chatGuild = StringUtils.colored(pc.getString("chat-guild"));
        this.chatRank = StringUtils.colored(pc.getString("chat-rank"));
        this.chatPoints = StringUtils.colored(pc.getString("chat-points"));
        this.chatPriv = pc.getString("chat-priv");
        this.chatAlly = pc.getString("chat-ally");
        this.chatGlobal = pc.getString("chat-global");
        this.chatPrivDesign = StringUtils.colored(pc.getString("chat-priv-design"));
        this.chatAllyDesign = StringUtils.colored(pc.getString("chat-ally-design"));
        this.chatGlobalDesign = StringUtils.colored(pc.getString("chat-global-design"));

        // Rank Section
        this.rankStart = pc.getInt("rank-start");
        this.rankPercent = pc.getDouble("rank-percent");
        if (this.rankPercent == 0)
            this.rankPercent = 1.0;

        // Damage Section
        this.damageGuild = pc.getBoolean("damage-guild");
        this.damageAlly = pc.getBoolean("damage-ally");

        // Base Section
        this.baseEnable = pc.getBoolean("base-enable");
        if (this.baseEnable) {
            list = pc.getStringList("base-items");
            items = new ArrayList<ItemStack>();
            for (String item : list) {
                ItemStack itemstack = Parser.parseItem(item);
                if (itemstack != null)
                    items.add(itemstack);
            }
            this.baseItems = items;
            this.baseDelay = pc.getInt("base-delay");
        }

        // Explosion Section
        this.explodeRadius = pc.getInt("explode-radius");
        HashMap<Material, Double> map = new HashMap<>();
        for (String path : pc.getSectionKeys("explode-materials")) {
            Material material = Parser.parseMaterial(path);
            if (material == null || material == Material.AIR)
                continue;
            double chance = pc.getDouble("explode-materials." + path);
            if (chance == 0)
                continue;
            map.put(material, chance);
        }
        this.explodeMaterials = map;

        // PlayerList Section
        String[] ss = new String[60];
        for (String path : pc.getSectionKeys("player-list")) {
            try {
                int i = Integer.parseInt(path);
                if (i > 60)
                    continue;
                String s = pc.getString("player-list." + path);
                if (s != null)
                    s = StringUtils.colored(s);
                ss[i - 1] = s;
            } catch (NumberFormatException e) {
                FunnyLog.parser("[Settings] Unknown number: " + path);
            }
        }
        new PlayerListScheme(ss);
        this.playerlistEnable = pc.getBoolean("player-list-enable");
        this.playerlistInterval = pc.getInt("player-list-interval");
        this.playerlistPing = pc.getInt("player-list-ping");
        this.playerlistPatch = pc.getBoolean("player-list-patch");
        this.playerlistPoints = StringUtils.colored(pc.getString("player-list-points"));
        PlayerListManager.enable(this.playerlistEnable);
        PlayerListManager.patch(this.playerlistPatch);
        PlayerListManager.ping(this.playerlistPing);

        // Commands Section
        this.excCreate = pc.getString("commands.create.name");
        this.excDelete = pc.getString("commands.delete.name");
        this.excConfirm = pc.getString("commands.confirm.name");
        this.excInvite = pc.getString("commands.invite.name");
        this.excJoin = pc.getString("commands.join.name");
        this.excLeave = pc.getString("commands.leave.name");
        this.excKick = pc.getString("commands.kick.name");
        this.excBase = pc.getString("commands.base.name");
        this.excEnlarge = pc.getString("commands.enlarge.name");
        this.excGuild = pc.getString("commands.guild.name");
        this.excAlly = pc.getString("commands.ally.name");
        this.excBreak = pc.getString("commands.break.name");
        this.excInfo = pc.getString("commands.info.name");
        this.excPlayer = pc.getString("commands.player.name");
        this.excTop = pc.getString("commands.top.name");
        this.excValidity = pc.getString("commands.validity.name");
        this.excLeader = pc.getString("commands.leader.name");
        this.excDeputy = pc.getString("commands.deputy.name");
        this.excRanking = pc.getString("commands.ranking.name");

        this.mxcPvP = pc.getString("commands.pvp.name");
        this.mxcBase = pc.getString("commands.setbase.name");

        this.excCreateAliases = pc.getStringList("commands.create.aliases");
        this.excDeleteAliases = pc.getStringList("commands.delete.aliases");
        this.excConfirmAliases = pc.getStringList("commands.confirm.aliases");
        this.excInviteAliases = pc.getStringList("commands.invite.aliases");
        this.excJoinAliases = pc.getStringList("commands.join.aliases");
        this.excLeaveAliases = pc.getStringList("commands.leave.aliases");
        this.excKickAliases = pc.getStringList("commands.kick.aliases");
        this.excBaseAliases = pc.getStringList("commands.base.aliases");
        this.excEnlargeAliases = pc.getStringList("commands.enlarge.aliases");
        this.excGuildAliases = pc.getStringList("commands.guild.aliases");
        this.excAllyAliases = pc.getStringList("commands.ally.aliases");
        this.excBreakAliases = pc.getStringList("commands.break.aliases");
        this.excInfoAliases = pc.getStringList("commands.info.aliases");
        this.excPlayerAliases = pc.getStringList("commands.player.aliases");
        this.excTopAliases = pc.getStringList("commands.top.aliases");
        this.excValidityAliases = pc.getStringList("commands.validity.aliases");
        this.excLeaderAliases = pc.getStringList("commands.leader.aliases");
        this.excDeputyAliases = pc.getStringList("commands.deputy.aliases");
        this.excRankingAliases = pc.getStringList("commands.ranking.aliases");

        this.mxcPvPAliases = pc.getStringList("commands.pvp.aliases");
        this.mxcBaseAliases = pc.getStringList("commands.setbase.aliases");

        this.axcMain = pc.getString("commands.admin.main");
        this.axcAdd = pc.getString("commands.admin.add");
        this.axcDelete = pc.getString("commands.admin.delete");
        this.axcKick = pc.getString("commands.admin.kick");
        this.axcTeleport = pc.getString("commands.admin.teleport");
        this.axcPoints = pc.getString("commands.admin.points");
        this.axcKills = pc.getString("commands.admin.kills");
        this.axcDeaths = pc.getString("commands.admin.deaths");
        this.axcBan = pc.getString("commands.admin.ban");
        this.axcUnban = pc.getString("commands.admin.unban");
        this.axcLives = pc.getString("commands.admin.lives");
        this.axcMove = pc.getString("commands.admin.move");
        this.axcValidity = pc.getString("commands.admin.validity");
        this.axcName = pc.getString("commands.admin.name");

        // Data Section
        this.dataInterval = pc.getInt("data-interval");
        this.flat = pc.getBoolean("data-type.flat");
        this.mysql = pc.getBoolean("data-type.mysql");
        if (this.mysql) {
            this.mysqlHostname = pc.getString("mysql.hostname");
            this.mysqlPort = pc.getString("mysql.port");
            this.mysqlDatabase = pc.getString("mysql.database");
            this.mysqlUser = pc.getString("mysql.user");
            this.mysqlPassword = pc.getString("mysql.password");
        }
    }
}
