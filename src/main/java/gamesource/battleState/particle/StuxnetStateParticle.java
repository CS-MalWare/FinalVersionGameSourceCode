package gamesource.battleState.particle;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class StuxnetStateParticle {
    private static ParticleEmitter flash;
    public static ParticleEmitter getParticle1(AssetManager assetManager){
        flash = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);

        // 粒子的生存周期
        flash.setLowLife(0.2f);// 最短
        flash.setHighLife(0.4f);// 最长

        /**
         * 粒子的外观
         */
        // 加载材质
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/flash.png"));
        flash.setMaterial(mat);

        // 设置1x1的动画
        flash.setImagesX(1);
        flash.setImagesY(1);
        flash.setLocalTranslation(2.2f, 0, 1); //这个坐标能放在第一个怪物身上
//        flash.setLocalTranslation(1.35f, -0.7f, 1);
        // 初始颜色，结束颜色
        flash.setEndColor(new ColorRGBA(1f, 1f, 1f, 0.5f));
        flash.setStartColor(new ColorRGBA(0f, 0f, 0f, 0.5f));

        // 初始大小，结束大小
        flash.setStartSize(1f);
        flash.setEndSize(0.5f);

        /**
         * 粒子的行为
         */
        // 初速度
        flash.getParticleInfluencer().setInitialVelocity(new Vector3f(1, 0, 0));
        // 速度的变化
        flash.getParticleInfluencer().setVelocityVariation(0.3f);
        // 不受重力影响
        flash.setGravity(-9.8f, 0, 0);
        return flash;
    }
}
