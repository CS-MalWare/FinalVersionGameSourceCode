package gamesource.battleState.card;

import gamesource.battleState.card.neutral.attack.*;
import gamesource.battleState.card.neutral.skill.*;
import gamesource.battleState.card.saber.attack.*;
import gamesource.battleState.card.saber.power.CounterStrikeGesture;
import gamesource.battleState.card.saber.power.DivineDefense;
import gamesource.battleState.card.saber.power.HideTheSword;
import gamesource.battleState.card.saber.power.ManaBoost;
import gamesource.battleState.card.saber.skill.*;

// 主要用于随机获取卡牌
public class CreateCard {

    // 获得普通卡的概率为55%
    private static final double getCommonCard = 1f;
    // 获得稀有卡的概率为20%
    private static final double getRareCard = 0.45f;
    // 获得史诗卡的概率为15%
    private static final double getEpicCard = 0.25f;
    // 获得传说卡的概率为10%
    private static final double getLegendaryCard = 0.1f;

    //传入卡牌名称和卡牌类型
    public static Card createCard(String cardName, Card.TYPE type) {
        return null;
    }


    public static Card getRandomCard(Card.OCCUPATION occupation) {
        double random = Math.random();
        if (random < getLegendaryCard) {
            return getRandomLegendaryCard(occupation);
        }
        if (random > getLegendaryCard && random < getEpicCard) {
            return getRandomEpicCard(occupation);
        }
        if (random > getEpicCard && random < getRareCard) {
            return getRandomRareCard(occupation);
        }
        return getRandomCommonCard(occupation);


    }

    private static Card getRandomCommonCard(Card.OCCUPATION occupation) {
        if (occupation == Card.OCCUPATION.NEUTRAL) {
            int cardID = (int) (Math.random() * 15) + 1;
            switch (cardID) {
                case 1:
                    return new ConeFlame();
                case 2:
                    return new ConeGold();
                case 3:
                    return new HookBoxing();
                case 4:
                    return new KickDown();
                case 5:
                    return new SoilWaveSpells();
                case 6:
                    return new SoulSacrifice();
                case 7:
                    return new TheKissOfDeath();
                case 8:
                    return new Crouch();
                case 9:
                    return new Crystallization();
                case 10:
                    return new Intelligent();
                case 11:
                    return new SecretAttack();
                case 12:
                    return new SecretPower();
                case 13:
                    return new SecretSkill();
                case 14:
                    return new ShieldWall();
                case 15:
                    return new Twice();
                default:
                    return null;


            }
        } else if (occupation == Card.OCCUPATION.SABER) {
            int cardID = (int) (Math.random() * 28) + 1;
            switch (cardID) {
                case 1:
                    return new BladeDance();
                case 2:
                    return new BlastStrike();
                case 3:
                    return new DoubleBladeChop();
                case 4:
                    return new DrawSwordStrike();
                case 5:
                    return new FireSlash();
                case 6:
                    return new FlameChop();
                case 7:
                    return new GoldSlash();
                case 8:
                    return new IceSlash();
                case 9:
                    return new LightSlash();
                case 10:
                    return new MagicalLIghtSlash();
                case 11:
                    return new Slash();
                case 12:
                    return new SoilSlash();
                case 13:
                    return new StaggeringBlow();
                case 14:
                    return new WoundedStrike();
                case 15:
                    return new AccumulateEnergy();
                case 16:
                    return new Alleys();
                case 17:
                    return new Defense();
                case 18:
                    return new Flying();
                case 19:
                    return new GrindingSword();
                case 20:
                    return new Heal();
                case 21:
                    return new LifeSwordEnchanting();
                case 22:
                    return new NibbleDrumsticks();
                case 23:
                    return new Peripateticism();
                case 24:
                    return new RaiseShield();
                case 25:
                    return new Regenerate();
                case 26:
                    return new SeeThrough();
                case 27:
                    return new SuckFinger();
                case 28:
                    return new WhirlingShield();
                default:
                    return null;

            }
        } else {
            double random = Math.random();
            if (random < 0.67) {
                return getRandomCommonCard(Card.OCCUPATION.SABER);
            } else {
                return getRandomCommonCard(Card.OCCUPATION.NEUTRAL);
            }
        }
    }

    private static Card getRandomRareCard(Card.OCCUPATION occupation) {
        if (occupation == Card.OCCUPATION.NEUTRAL) {
            int cardID = (int) (Math.random() * 6) + 1;
            switch (cardID) {
                case 1:
                    return new StarMeteor();
                case 2:
                    return new Whirlpool();
                case 3:
                    return new WindingByWoodSpirit();
                case 4:
                    return new PickAndTease();
                case 5:
                    return new Tenacity();
                case 6:
                    return new Unyielding();
                default:
                    return null;


            }
        } else if (occupation == Card.OCCUPATION.SABER) {
            int cardID = (int) (Math.random() * 13) + 1;
            switch (cardID) {
                case 1:
                    return new CoreCross();
                case 2:
                    return new RedFlameChop();
                case 3:
                    return new SuperDrawSwordSlash();
                case 4:
                    return new Surmount();
                case 5:
                    return new TriumphantReturn();
                case 6:
                    return new EmpireSwordArt();
                case 7:
                    return new IceMagicShield();
                case 8:
                    return new MagicSheild();
                case 9:
                    return new NuclearShield();
                case 10:
                    return new ShieldTrigger();
                case 11:
                    return new TakeAnotherShield();
                case 12:
                    return new DivineDefense();
                case 13:
                    return new ManaBoost();
                default:
                    return null;

            }
        } else {
            double random = Math.random();
            if (random < 0.67) {
                return getRandomRareCard(Card.OCCUPATION.SABER);
            } else {
                return getRandomRareCard(Card.OCCUPATION.NEUTRAL);
            }
        }
    }

    private static Card getRandomEpicCard(Card.OCCUPATION occupation) {
        if (occupation == Card.OCCUPATION.NEUTRAL) {
            int cardID = (int) (Math.random() * 2) + 1;
            switch (cardID) {
                case 1:
                    return new GreedHand();
                case 2:
                    return new Bomb();
                default:
                    return null;

            }
        } else if (occupation == Card.OCCUPATION.SABER) {
            int cardID = (int) (Math.random() * 11) + 1;
            switch (cardID) {
                case 1:
                    return new DarkIceTrap();
                case 2:
                    return new LightChoppingSword();
                case 3:
                    return new SnakeSkinOperation();
                case 4:
                    return new SorcerersSword();
                case 5:
                    return new CanyingShadow();
                case 6:
                    return new EnergySplash();
                case 7:
                    return new OutOfBody();
                case 8:
                    return new PrayForLight();
                case 9:
                    return new CounterStrikeGesture();
                case 10:
                    return new HideTheSword();
                case 11:
                    return new ShieldBash();
                default:
                    return null;

            }
        } else {
            double random = Math.random();
            if (random < 0.67) {
                return getRandomEpicCard(Card.OCCUPATION.SABER);
            } else {
                return getRandomEpicCard(Card.OCCUPATION.NEUTRAL);
            }
        }
    }

    private static Card getRandomLegendaryCard(Card.OCCUPATION occupation) {
        if (occupation == Card.OCCUPATION.NEUTRAL) {
            int cardID = (int) (Math.random() * 2) + 1;
            switch (cardID) {
                case 1:
                    return new Enchantment();
                case 2:
                    return new Winding();
                default:
                    return null;
            }
        } else if (occupation == Card.OCCUPATION.SABER) {
            int cardID = (int) (Math.random() * 4) + 1;
            switch (cardID) {
                case 1:
                    return new InstantSlash();
                case 2:
                    return new DeathTogether();
                case 3:
                    return new Rampage();
                case 4:
                    return new SuperConversion();
                default:
                    return null;
            }
        } else {
            double random = Math.random();
            if (random < 0.67) {
                return getRandomLegendaryCard(Card.OCCUPATION.SABER);
            } else {
                return getRandomLegendaryCard(Card.OCCUPATION.NEUTRAL);
            }
        }

    }


}
