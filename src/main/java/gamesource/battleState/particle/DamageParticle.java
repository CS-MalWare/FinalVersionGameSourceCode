package gamesource.battleState.particle;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class DamageParticle {
    // 这个特效用于给怪物加一个霸气的血色光环特效,当怪物要造成巨量伤害时调用
    private static ParticleEmitter blood;
    public static ParticleEmitter getParticle1(AssetManager assetManager){
        blood = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);

        // 粒子的生存周期
        blood.setLowLife(0.5f);// 最短
        blood.setHighLife(0.9f);// 最长

        /**
         * 粒子的外观
         */
        // 加载材质
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/blood.png"));
        blood.setMaterial(mat);

        // 设置1x1的动画
        blood.setImagesX(1);
        blood.setImagesY(1);
        blood.setLocalTranslation(2.5f, -1, 1); //这个坐标能放在第一个怪物身上
//        blood.setLocalTranslation(1.35f, -0.7f, 1);
        // 初始颜色，结束颜色
        blood.setEndColor(new ColorRGBA(1f, 0f, 0f, 0.2f)); // red
        blood.setStartColor(new ColorRGBA(1f, 0f, 0f, 0.2f)); // red

        // 初始大小，结束大小
        blood.setStartSize(1f);
        blood.setEndSize(0.5f);

        /**
         * 粒子的行为
         */
        // 初速度
        blood.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        // 速度的变化
        blood.getParticleInfluencer().setVelocityVariation(0.3f);
        // 不受重力影响
        blood.setGravity(0, 0, 0);
        return blood;
    }
    public static ParticleEmitter getParticle2(AssetManager assetManager){
        blood = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);

        // 粒子的生存周期
        blood.setLowLife(0.5f);// 最短
        blood.setHighLife(0.9f);// 最长

        /**
         * 粒子的外观
         */
        // 加载材质
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/blood.png"));
        blood.setMaterial(mat);

        // 设置1x1的动画
        blood.setImagesX(1);
        blood.setImagesY(1);
        blood.setLocalTranslation(4, -1, 1); //这个坐标能放在第一个怪物身上
//        blood.setLocalTranslation(3.35f, -0.7f, 1);
        // 初始颜色，结束颜色
        blood.setEndColor(new ColorRGBA(1f, 0f, 0f, 0.2f)); // red
        blood.setStartColor(new ColorRGBA(1f, 0f, 0f, 0.2f)); // red

        // 初始大小，结束大小
        blood.setStartSize(1f);
        blood.setEndSize(0.5f);

        /**
         * 粒子的行为
         */
        // 初速度
        blood.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        // 速度的变化
        blood.getParticleInfluencer().setVelocityVariation(0.3f);
        // 不受重力影响
        blood.setGravity(0, 0, 0);
        return blood;
    }
    public static ParticleEmitter getParticle3(AssetManager assetManager){
        blood = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);

        // 粒子的生存周期
        blood.setLowLife(0.5f);// 最短
        blood.setHighLife(0.9f);// 最长

        /**
         * 粒子的外观
         */
        // 加载材质
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/blood.png"));
        blood.setMaterial(mat);

        // 设置1x1的动画
        blood.setImagesX(1);
        blood.setImagesY(1);
        blood.setLocalTranslation(5.5f, -1, 1); //这个坐标能放在第一个怪物身上
//        blood.setLocalTranslation(3.35f, -0.7f, 1);
        // 初始颜色，结束颜色
        blood.setEndColor(new ColorRGBA(1f, 0f, 0f, 0.2f)); // red
        blood.setStartColor(new ColorRGBA(1f, 0f, 0f, 0.2f)); // red

        // 初始大小，结束大小
        blood.setStartSize(1f);
        blood.setEndSize(0.5f);

        /**
         * 粒子的行为
         */
        // 初速度
        blood.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        // 速度的变化
        blood.getParticleInfluencer().setVelocityVariation(0.3f);
        // 不受重力影响
        blood.setGravity(0, 0, 0);
        return blood;
    }
}
