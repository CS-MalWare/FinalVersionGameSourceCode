package gamesource.battleState.particle;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class MPParticle {
    private static ParticleEmitter flame;
    public static ParticleEmitter getParticle1(AssetManager assetManager){
        flame = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);

        // 粒子的生存周期
        flame.setLowLife(1f);// 最短
        flame.setHighLife(1.4f);// 最长

        /**
         * 粒子的外观
         */
        // 加载材质
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/flame.png"));
        flame.setMaterial(mat);

        // 设置2x2的动画
        flame.setImagesX(2);
        flame.setImagesY(2);
        flame.setLocalTranslation(-4.65f, -2.14f, 1);
        // 初始颜色，结束颜色
        flame.setEndColor(new ColorRGBA(1f, 0f, 0f, 1f)); // red
        flame.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow

        // 初始大小，结束大小
        flame.setStartSize(1.5f);
        flame.setEndSize(0.5f);

        /**
         * 粒子的行为
         */
        // 初速度
        flame.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        // 速度的变化
        flame.getParticleInfluencer().setVelocityVariation(0.3f);
        // 不受重力影响
        flame.setGravity(0, 0, 0);
        return flame;
    }
}
