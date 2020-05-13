package gamesource.battleState.control;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;


/**
 * 这是一个运动控件，其作用是让模型朝目标点直线运动。
 *
 * @author yanmaoyuan
 */
public class UseCardControl extends AbstractControl {

    // 运动速度
    private float walkSpeed = 800f;
    private float speedFactor = 1.0f;

    // 运动的方向向量
    private Vector2f  walkDir;
    // 运动一步的向量
    private Vector2f step;

    // 三维空间中的z
    private float z;

    // 当前位置
    private Vector2f loc;
    // 目标位置
    private Vector2f target;

    // 旋转速度
    private float rotateSpeed;

    private float rotatedist = (float) (0.25  * Math.PI);

    private int stage= 1;




    public UseCardControl(Spatial spatial) {
        this(1400f, 0.2f,spatial);
    }

    public UseCardControl(float walkSpeed, float rotateSpeed,Spatial spatial) {
        this.walkSpeed = walkSpeed;
        this.rotateSpeed = rotateSpeed;
        walkDir = null;
        target = null;
        loc = new Vector2f();
        step = new Vector2f();
        this.stage=1;
        this.setSpatial(spatial);
        this.setTarget(new Vector2f(700,320));
    }

    /**
     * 设置运动速度
     *
     * @param walkSpeed
     */
    public void setWalkSpeed(float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

    public void setSpeedFactor(float speedFactor) {
        this.speedFactor = speedFactor;
    }

    /**
     * 设置目标点
     *
     * @param target
     */
    public void setTarget(Vector2f target) {
        this.target = target;
        if (target == null) {
            walkDir = null;
            return;
        }
        loc.x=spatial.getLocalTranslation().x;
        loc.y=spatial.getLocalTranslation().y;
        this.walkDir = target.subtract(this.loc);
//        System.out.printf("%f, %f\n",walkDir.x,walkDir.y);
        this.walkDir.normalizeLocal();

    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        if (spatial==null) return;
        // 初始化位置
        Vector3f location = spatial.getLocalTranslation();
        this.loc = new Vector2f(location.x, location.y);
        z = location.z;
    }


    /**
     * 重写主循环，让这个模型向目标点移动。
     */
    @Override
    protected void controlUpdate(float tpf) {

        switch (this.stage){
            case 1:
                if (walkDir != null ) {
                    // 计算下一步的步长
                    float stepDist = walkSpeed * tpf * speedFactor;


                    if (stepDist == 0f) {
                        return;
                    }

                    // 计算离目标点的距离
                    float dist = loc.distance(target);


                    if (stepDist <= dist) {
                        // 计算位移
//                System.out.println("moved");
                        walkDir.mult(stepDist, step);
                        loc.addLocal(step);

                        Vector3f next = new Vector3f(loc.x, loc.y, this.z);
                        spatial.setLocalTranslation(next);
//                System.out.printf("name: %s, x: %f, y: %f\n",spatial.getName(),next.x,next.y);
                    } else {
                        // 可以到达目标点
//                System.out.println("reach");
                        walkDir = null;
                        spatial.setLocalTranslation(target.x, target.y, this.z);
                        target = null;
                        stage=2;
                    }

                }
                break;

            case 2:
                float stepDist = rotateSpeed  * speedFactor;

                if (stepDist<rotatedist){
                    rotatedist-=stepDist;
                    spatial.rotate(-stepDist,stepDist,0);
                }else{
                    spatial.rotate(rotatedist,rotatedist,0);
                    this.setTarget(new Vector2f(1500,70));
                    this.setSpeedFactor(2f);
                    stage=3;
                }
                break;
            case 3:
                if (walkDir != null ) {
                    // 计算下一步的步长
                     stepDist = walkSpeed * tpf * speedFactor;


                    if (stepDist == 0f) {
                        return;
                    }

                    // 计算离目标点的距离
                    float dist = loc.distance(target);


                    if (stepDist <= dist) {
                        // 计算位移
//                System.out.println("moved");
                        walkDir.mult(stepDist, step);
                        loc.addLocal(step);

                        Vector3f next = new Vector3f(loc.x, loc.y, this.z);
                        spatial.setLocalTranslation(next);
                        spatial.rotate(rotateSpeed/5,rotateSpeed/5,this.rotateSpeed/2);

//                System.out.printf("name: %s, x: %f, y: %f\n",spatial.getName(),next.x,next.y);
                    } else {
                        // 可以到达目标点
//                System.out.println("reach");
                        walkDir = null;
                        spatial.setLocalTranslation(target.x, target.y, this.z);
                        target = null;
                        spatial.removeFromParent();
                        spatial.removeControl(this);
                    }

                }
                break;

        }

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}