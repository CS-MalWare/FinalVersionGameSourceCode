package gamesource.util;

import gamesource.battleState.card.Card;
import gamesource.battleState.card.neutral.attack.*;
import gamesource.battleState.card.neutral.skill.*;
import gamesource.battleState.card.saber.attack.*;
import gamesource.battleState.card.saber.power.CounterStrikeGesture;
import gamesource.battleState.card.saber.power.DivineDefense;
import gamesource.battleState.card.saber.power.HideTheSword;
import gamesource.battleState.card.saber.power.ManaBoost;
import gamesource.battleState.card.saber.skill.*;
import gamesource.battleState.character.MainRole;
import gamesource.battleState.equipment.Equipment;
import gamesource.battleState.equipment.common.*;
import gamesource.battleState.equipment.epic.*;
import gamesource.battleState.equipment.legendary.*;
import gamesource.battleState.equipment.rare.*;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class Storage {
    // 需要存档的属性一览
    // 1. 人物最大血量
    // 2. 人物当前血量
    // 3. 卡组信息
    // 4. 金币数量
    // 5. 人物装备
    // 6. 人物坐标（需要大🐅的提供人物位置信息）
    // 7. 打过的怪


    // 创建存档的方法 返回为true就是存档成功 返回为false就是存档失败
    public static boolean load() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("storage.json"));
            String str = null;// 预留的用于读取存档的变量
            String data = "";// 用于暂存json字符串数据的变量
            while ((str = br.readLine()) != null) {
                data += str + '\n';
            }
            JSONObject dataJson = new JSONObject(data);
            MainRole.getInstance().setTotalHP(dataJson.getInt("totalHP"));
            MainRole.getInstance().setHP(dataJson.getInt("HP"));

            // 处理卡组加载
            String deck[] = dataJson.getString("deck").split("\\|");
            ArrayList<Card> cardList = new ArrayList<Card>();
            for (String cardDetail : deck) {
                String upGrade = cardDetail.split(",")[0];//标识卡牌是否升级了
                String cardName = cardDetail.split(",")[1];//标识卡牌名称
                Card tempCard = null;
                switch (cardName) {
                    case "艳阳锥":
                        tempCard = new ConeFlame();
                        break;
                    case "凝金钻":
                        tempCard = new ConeGold();
                        break;
                    case "贪婪之手":
                        tempCard = new GreedHand();
                        break;
                    case "上勾拳":
                        tempCard = new HookBoxing();
                        break;
                    case "下段踢":
                        tempCard = new KickDown();
                        break;
                    case "土浪术":
                        tempCard = new SoilWaveSpells();
                        break;
                    case "千魂祭":
                        tempCard = new SoulSacrifice();
                        break;
                    case "星陨":
                        tempCard = new StarMeteor();
                        break;
                    case "黯灭吻":
                        tempCard = new TheKissOfDeath();
                        break;
                    case "旋涡":
                        tempCard = new Whirlpool();
                        break;
                    case "木灵缠绕":
                        tempCard = new WindingByWoodSpirit();
                        break;
                    case "炸弹":
                        tempCard = new Bomb();
                        break;
                    case "充钱":
                        tempCard = new ChargeMoney();
                        break;
                    case "蹲伏":
                        tempCard = new Crouch();
                        break;
                    case "晶化":
                        tempCard = new Crystallization();
                        break;
                    case "魔化":
                        tempCard = new Enchantment();
                        break;
                    case "机制":
                        tempCard = new Intelligent();
                        break;
                    case "挑谑":
                        tempCard = new PickAndTease();
                        break;
                    case "秘密攻击":
                        tempCard = new SecretAttack();
                        break;
                    case "秘密力量":
                        tempCard = new SecretPower();
                        break;
                    case "秘密技能":
                        tempCard = new SecretSkill();
                        break;
                    case "盾墙":
                        tempCard = new ShieldWall();
                        break;
                    case "坚定不移":
                        tempCard = new Tenacity();
                        break;
                    case "分身":
                        tempCard = new Twice();
                        break;
                    case "不屈":
                        tempCard = new Unyielding();
                        break;
                    case "神化":
                        tempCard = new Winding();
                        break;
                    case "剑舞":
                        tempCard = new BladeDance();
                        break;
                    case "奋力打击":
                        tempCard = new BlastStrike();
                        break;
                    case "嗜血斩":
                        tempCard = new BloodthirstyChop();
                        break;
                    case "十字斩":
                        tempCard = new CoreCross();
                        break;
                    case "冥海玄冰阵":
                        tempCard = new DarkIceTrap();
                        break;
                    case "双刀斩":
                        tempCard = new DoubleBladeChop();
                        break;
                    case "拔刀斩":
                        tempCard = new DrawSwordStrike();
                        break;
                    case "火焰斩":
                        tempCard = new FireSlash();
                        break;
                    case "烈焰斩":
                        tempCard = new FlameChop();
                        break;
                    case "撒币":
                        tempCard = new GoldSlash();
                        break;
                    case "冰魄斩":
                        tempCard = new IceSlash();
                        break;
                    case "瞬斩":
                        tempCard = new InstantSlash();
                        break;
                    case "光剑斩":
                        tempCard = new LightChoppingSword();
                        break;
                    case "光之挥舞":
                        tempCard = new LightSlash();
                        break;
                    case "魔光斩":
                        tempCard = new MagicalLIghtSlash();
                        break;
                    case "烈火斩":
                        tempCard = new RedFlameChop();
                        break;
                    case "打击":
                        tempCard = new Slash();
                        break;
                    case "蛇皮操作":
                        tempCard = new SnakeSkinOperation();
                        break;
                    case "裂地斩":
                        tempCard = new SoilSlash();
                        break;
                    case "魔剑术":
                        tempCard = new SorcerersSword();
                        break;
                    case "当头一击":
                        tempCard = new StaggeringBlow();
                        break;
                    case "破空拔刀斩":
                        tempCard = new SuperDrawSwordSlash();
                        break;
                    case "超越":
                        tempCard = new Surmount();
                        break;
                    case "凯旋":
                        tempCard = new TriumphantReturn();
                        break;
                    case "负伤打击":
                        tempCard = new WoundedStrike();
                        break;
                    case "逆转反击":
                        tempCard = new CounterStrikeGesture();
                        break;
                    case "神御格挡":
                        tempCard = new DivineDefense();
                        break;
                    case "藏剑":
                        tempCard = new HideTheSword();
                        break;
                    case "魔力激发":
                        tempCard = new ManaBoost();
                        break;
                    case "蓄势":
                        tempCard = new AccumulateEnergy();
                        break;
                    case "苟活":
                        tempCard = new Alleys();
                        break;
                    case "残影":
                        tempCard = new CanyingShadow();
                        break;
                    case "充能":
                        tempCard = new Charge();
                        break;
                    case "同生共死":
                        tempCard = new DeathTogether();
                        break;
                    case "格挡":
                        tempCard = new Defense();
                        break;
                    case "帝国剑术":
                        tempCard = new EmpireSwordArt();
                        break;
                    case "能量飞溅":
                        tempCard = new EnergySplash();
                        break;
                    case "飞行":
                        tempCard = new Flying();
                        break;
                    case "磨剑":
                        tempCard = new GrindingSword();
                        break;
                    case "治疗":
                        tempCard = new Heal();
                        break;
                    case "冰灵盾":
                        tempCard = new IceMagicShield();
                        break;
                    case "生命剑术":
                        tempCard = new LifeSwordEnchanting();
                        break;
                    case "魔法盾":
                        tempCard = new MagicSheild();
                        break;
                    case "啃鸡腿":
                        tempCard = new NibbleDrumsticks();
                        break;
                    case "核力护盾":
                        tempCard = new NuclearShield();
                        break;
                    case "灵魂出窍":
                        tempCard = new OutOfBody();
                        break;
                    case "逍遥游":
                        tempCard = new Peripateticism();
                        break;
                    case "光之祈祷":
                        tempCard = new PrayForLight();
                        break;
                    case "举盾":
                        tempCard = new RaiseShield();
                        break;
                    case "暴走":
                        tempCard = new Rampage();
                        break;
                    case "重振":
                        tempCard = new Regenerate();
                        break;
                    case "缄咒":
                        tempCard = new SealingSpell();
                        break;
                    case "看破":
                        tempCard = new SeeThrough();
                        break;
                    case "盾牌猛击":
                        tempCard = new ShieldBash();
                        break;
                    case "盾牌激发":
                        tempCard = new ShieldTrigger();
                        break;
                    case "吮手指":
                        tempCard = new SuckFinger();
                        break;
                    case "超能转化":
                        tempCard = new SuperConversion();
                        break;
                    case "再拿个盾":
                        tempCard = new TakeAnotherShield();
                        break;
                    case "旋盾":
                        tempCard = new WhirlingShield();
                        break;
                    default:
                        System.out.println("随机到了逸润巨佬没有做的卡牌");
                        break;
                }
                if (upGrade.equals("true")) {
                    tempCard.upgrade();
                }
                cardList.add(tempCard);
            }
            MainRole.getInstance().setDeck_(cardList);

            MainRole.getInstance().setGold(dataJson.getInt("gold"));

            // 处理装备加载
            String equipment[] = dataJson.getString("equipments").split("\\|");
            ArrayList<Equipment> equipmentList = new ArrayList<Equipment>();
            for (String equipmentPath : equipment) {
                switch (equipmentPath) {
                    case "电池":
                        equipmentList.add(new Battery());
                        break;
                    case "生物标本":
                        equipmentList.add(new BiologicalSample());
                        break;
                    case "破损的劳力士":
                        equipmentList.add(new BrokenRolex());
                        break;
                    case "折扣券":
                        equipmentList.add(new Coupon());
                        break;
                    case "诅咒魔法书":
                        equipmentList.add(new CurseBook());
                        break;
                    case "恶魔之锤":
                        equipmentList.add(new DemonHammer());
                        break;
                    case "活力圣瓶":
                        equipmentList.add(new DynamicAmpulla());
                        break;
                    case "磨刀石":
                        equipmentList.add(new KnifeStone());
                        break;
                    case "骑士手套":
                        equipmentList.add(new KnightGloves());
                        break;
                    case "一卡通":
                        equipmentList.add(new MetroCard());
                        break;
                    case "魔法石":
                        equipmentList.add(new PhilosopherStone());
                        break;
                    case "毒药丸":
                        equipmentList.add(new PoisonPill());
                        break;
                    case "瓜皮":
                        equipmentList.add(new Rind());
                        break;
                    case "推销手册":
                        equipmentList.add(new SellManualOfGSW());
                        break;
                    case "尖铅笔":
                        equipmentList.add(new SharpenedPencil());
                        break;
                    case "小盾牌":
                        equipmentList.add(new SmallShield());
                        break;
                    case "蛇皮袋":
                        equipmentList.add(new SnakeskinBag());
                        break;
                    case "带刺的毛衣":
                        equipmentList.add(new SweaterWithThorns());
                        break;
                    case "阿姆斯特朗回旋炮":
                        equipmentList.add(new ArmstrongGun());
                        break;
                    case "隐形斗篷":
                        equipmentList.add(new InvisibilityCloak());
                        break;
                    case "万花筒":
                        equipmentList.add(new Kaleidoscope());
                        break;
                    case "阿拉丁神灯":
                        equipmentList.add(new LampOfAladdin());
                        break;
                    case "FF团的大旗":
                        equipmentList.add(new TheFlagOfTheFFRegiment());
                        break;
                    case "五年高考三年模拟":
                        equipmentList.add(new ThreeYearSimulationFiveYearCollegeEntranceExamination());
                        break;
                    case "高士味的钱包":
                        equipmentList.add(new WalletOfGSW());
                        break;
                    case "负重":
                        equipmentList.add(new WeightBearing());
                        break;
                    case "陈宇轩的女装":
                        equipmentList.add(new WomenClothesOfCYX());
                        break;
                    case "王逸润的steam账号":
                        equipmentList.add(new WYRSteamAccount());
                        break;
                    case "平衡的天秤":
                        equipmentList.add(new BalancedLibra());
                        break;
                    case "春哥甲":
                        equipmentList.add(new ChunGeAmor());
                        break;
                    case "诅咒的宝箱":
                        equipmentList.add(new CurseChest());
                        break;
                    case "美杜莎的眼罩":
                        equipmentList.add(new MedusaEyeMask());
                        break;
                    case "梅林法袍":
                        equipmentList.add(new MerlinGown());
                        break;
                    case "梅林之靴":
                        equipmentList.add(new MerlinShoes());
                        break;
                    case "梅林法杖":
                        equipmentList.add(new MerlinWand());
                        break;
                    case "高傲的神话":
                        equipmentList.add(new TheMythOfPride());
                        break;
                    case "黑暗精灵的短笛":
                        equipmentList.add(new ThePiccoloOfTheDarkElves());
                        break;
                    case "紧身衣":
                        equipmentList.add(new Tights());
                        break;
                    case "魅高林的外星人":
                        equipmentList.add(new AlienwareOfWGL());
                        break;
                    case "连击星":
                        equipmentList.add(new ComboStar());
                        break;
                    case "诅咒的皮带":
                        equipmentList.add(new CursedBelt());
                        break;
                    case "陈宇轩的假发":
                        equipmentList.add(new CYXArtificialHair());
                        break;
                    case "鬼王披风":
                        equipmentList.add(new DevilPrinceCloak());
                        break;
                    case "任意门":
                        equipmentList.add(new DimensionDoor());
                        break;
                    case "烈火之栗":
                        equipmentList.add(new FireKernel());
                        break;
                    case "生命之核":
                        equipmentList.add(new LifeKernel());
                        break;
                    case "精金之铠":
                        equipmentList.add(new MetalKernal());
                        break;
                    case "爆米花":
                        equipmentList.add(new Popcorn());
                        break;
                    case "橡皮缓冲":
                        equipmentList.add(new RubberBuffer());
                        break;
                    case "海洋之心":
                        equipmentList.add(new SeaKernel());
                        break;
                    case "厚土之靴":
                        equipmentList.add(new SoilKernel());
                        break;
                    case "便签纸":
                        equipmentList.add(new StickyNote());
                        break;
                    case "龙牙盾":
                        equipmentList.add(new TheDragonTeethShield());
                        break;
                    default:
                        System.out.println("此处没有装备");
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("没有存档文件");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取出错");
            return false;
        }
        return true;
    }

    // 加载存档的方法 返回为true就是加载成功 返回为false就是加载失败
    public static boolean save() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("storage.json"));
            String data = "{\n";
            data += String.format("\"totalHP\":\"%s\",\n", MainRole.getInstance().getTotalHP());
            data += String.format("\"HP\":\"%s\",\n", MainRole.getInstance().getHP());

            // 临时存储每个卡组卡牌路径的变量
            String tempPath = "";
            for (Card card : MainRole.getInstance().getDeck_()) {
                tempPath += card.isUpgraded() ? "true," : "false,";
                tempPath += card.getUnchangeName() + "|";
            }

            data += String.format("\"deck\":\"%s\",\n", tempPath);
            data += String.format("\"gold\":\"%s\",\n", MainRole.getInstance().getGold());

            // 这次用这个变量临时存储装备路径
            tempPath = "";
            for (Equipment equipment : MainRole.getInstance().getEquipments()) {
                tempPath += equipment.getPicName() + "|";
            }
            data += String.format("\"equipments\":\"%s\"\n", tempPath);
            data += "}";
            bw.write(data);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("写入出错");
            return false;
        }
        return true;
    }

    // 测试用的主方法
    public static void main(String[] args) {
        save();
        load();
    }

}
