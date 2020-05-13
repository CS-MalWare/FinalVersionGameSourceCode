package gamesource.battleState.equipment;

import gamesource.battleState.equipment.common.*;
import gamesource.battleState.equipment.epic.*;
import gamesource.battleState.equipment.legendary.*;
import gamesource.battleState.equipment.rare.*;

import java.lang.reflect.Array;
import java.util.Arrays;

public class CreateEquipment {
    // 获得传说装备的概率为10%
    private static double getLegendaryEquipment = 0.1f;
    // 获得史诗装备的概率为15%
    private static double getEpicEquipment = 0.15f;
    // 获得稀有装备的概率为20%
    private static double getRareEquipment = 0.20f;
    // 获得普通装备的概率为55%
    private static double getCommonEquipment = 0.55f;
    // 爆率数组, 存储着所有的爆率,防止通过后期调整导致更稀有的装备爆率比普通装备高, 以至于不能正常获得装备
    private static double[] proArr = new double[4];
    // 用来标识是否更改过爆率的变量, 因为第一次相当于设置了一次变量,所以设置为 true, 用来减少上面爆率数组排序的次数
    private static boolean isChange = true;

    // 初始化爆率数组的方法
    private static void setProArr() {
        proArr[0] = getCommonEquipment;
        proArr[1] = getRareEquipment;
        proArr[2] = getEpicEquipment;
        proArr[3] = getLegendaryEquipment;
        Arrays.sort(proArr);
    }

    // start ----- 修改爆率的方法
    public static void setGetCommonEquipment(double newGetCommonEquipment) {
        getCommonEquipment = newGetCommonEquipment;
        isChange = true;
    }

    public static void setGetRareEquipment(double newGetRareEquipment) {
        getRareEquipment = getEpicEquipment + newGetRareEquipment;
        isChange = true;
    }

    public static void setGetEpicEquipment(double newGetEpicEquipment) {
        getEpicEquipment = getLegendaryEquipment + newGetEpicEquipment;
        isChange = true;
    }

    public static void setGetLegendaryEquipment(double newGetLegendaryEquipment) {
        getLegendaryEquipment = newGetLegendaryEquipment;
        isChange = true;
    }
    // end ----- 修改爆率方法

    // start ----- 获得装备方法
    public static Equipment getRandomEquipment() {
        if (isChange) {
            setProArr();
            isChange = false;
        }
        double random = Math.random() * (getRareEquipment + getCommonEquipment + getLegendaryEquipment + getEpicEquipment);
        if (random < proArr[0]) {
            return getRandomLegendaryEquipment();
        }
        if (random < proArr[1] + proArr[0]) {
            return getRandomEpicEquipment();
        }
        if (random < proArr[2] + proArr[1] + proArr[0]) {
            return getRandomRareEquipment();
        }
        if (random < proArr[3] + proArr[2] + proArr[1] + proArr[0]) {
            return getRandomCommonEquipment();
        }
        return null;
    }

    public static Equipment getRandomCommonEquipment() {
        int equipmentId = (int) (Math.random() * 18);
        switch (equipmentId) {
            case 1:
                return new Battery();
            case 2:
                return new BiologicalSample();
            case 3:
                return new BrokenRolex();
            case 4:
                return new Coupon();
            case 5:
                return new CurseBook();
            case 6:
                return new DemonHammer();
            case 7:
                return new DynamicAmpulla();
            case 8:
                return new KnifeStone();
            case 9:
                return new KnightGloves();
            case 10:
                return new MetroCard();
            case 11:
                return new PhilosopherStone();
            case 12:
                return new PoisonPill();
            case 13:
                return new Rind();
            case 14:
                return new SellManualOfGSW();
            case 15:
                return new SharpenedPencil();
            case 16:
                return new SmallShield();
            case 17:
                return new SnakeskinBag();
            default:
                return new SweaterWithThorns();
        }
    }

    public static Equipment getRandomRareEquipment() {
        int equipmentId = (int) (Math.random() * 15);
        switch (equipmentId) {
            case 1:
                return new AlienwareOfWGL();
            case 2:
                return new ComboStar();
            case 3:
                return new CursedBelt();
            case 4:
                return new CYXArtificialHair();
            case 5:
                return new DevilPrinceCloak();
            case 6:
                return new DimensionDoor();
            case 7:
                return new FireKernel();
            case 8:
                return new LifeKernel();
            case 9:
                return new MetalKernal();
            case 10:
                return new Popcorn();
            case 11:
                return new RubberBuffer();
            case 12:
                return new SeaKernel();
            case 13:
                return new SoilKernel();
            case 14:
                return new StickyNote();
            default:
                return new TheDragonTeethShield();
        }
    }

    public static Equipment getRandomEpicEquipment() {
        int equipmentId = (int) (Math.random() * 10);
        switch (equipmentId) {
            case 1:
                return new ArmstrongGun();
            case 2:
                return new InvisibilityCloak();
            case 3:
                return new Kaleidoscope();
            case 4:
                return new LampOfAladdin();
            case 5:
                return new TheFlagOfTheFFRegiment();
            case 6:
                return new ThreeYearSimulationFiveYearCollegeEntranceExamination();
            case 7:
                return new WalletOfGSW();
            case 8:
                return new WeightBearing();
            case 9:
                return new WomenClothesOfCYX();
            default:
                return new WYRSteamAccount();
        }
    }

    public static Equipment getRandomLegendaryEquipment() {
        int equipmentId = (int) (Math.random() * 10);
        switch (equipmentId) {
            case 1:
                return new BalancedLibra();
            case 2:
                return new ChunGeAmor();
            case 3:
                return new CurseChest();
            case 4:
                return new MedusaEyeMask();
            case 5:
                return new MerlinGown();
            case 6:
                return new MerlinShoes();
            case 7:
                return new MerlinWand();
            case 8:
                return new TheMythOfPride();
            case 9:
                return new ThePiccoloOfTheDarkElves();
            default:
                return new Tights();
        }
    }
    // end ----- 获得装备的方法
}
