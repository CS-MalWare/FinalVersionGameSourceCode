package gamesource.battleState.control;

import com.jme3.math.FastMath;
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
public class CardMotionControl extends AbstractControl {

    // 运动速度
    private float walkSpeed = 800f;
    private float speedFactor = 1.0f;

    // 运动的方向向量
    private Vector2f walkDir;
    // 运动一步的向量
    private Vector2f step;

    // 三维空间中的z
    private float z;

    // 当前位置
    private Vector2f loc;
    // 目标位置
    private Vector2f target;

    //当前旋转角度
    private float curAngle;

    //目标旋转角度
    private float tarAngle;

    // 旋转速度
    private float rotateSpeed;

    // 旋转目标方向
    private float rotateDir;

    public CardMotionControl() {
        this(200f, 0);
    }

    public CardMotionControl(float walkSpeed, float rotateSpeed) {
        this.walkSpeed = walkSpeed;
        this.rotateSpeed = rotateSpeed;
        walkDir = null;
        target = null;
        loc = new Vector2f();
        step = new Vector2f();

    }

    /**
     * 设置运动速度
     *
     * @param walkSpeed
     */
    public void setWalkSpeed(float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }


    /**
     * 设置目标点
     *
     * @param target
     */
    public void setTarget(Vector2f target, float tarAngle) {
        this.target = target;
        this.tarAngle = tarAngle;
        if (target == null) {
            walkDir = null;
            return;
        }

        if (tarAngle > curAngle) {
            rotateDir = 1.0f;
        } else {
            rotateDir = -1.0f;
        }
        // 计算运动方向

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
        this.curAngle = spatial.getLocalRotation().getZ() *2;

    }


    /**
     * 重写主循环，让这个模型向目标点移动。
     */
    @Override
    protected void controlUpdate(float tpf) {


        if (walkDir != null || rotateDir != 0) {

            // 计算下一步的步长
            float stepDist = walkSpeed * tpf * speedFactor;


            if (stepDist == 0f) {
                return;
            }

            // 计算离目标点的距离
            float dist = loc.distance(target);
            if (this.rotateSpeed == 0) {
//                System.out.println("initialize rotateSpeed");
                int times = (int) (dist / stepDist);
                this.rotateSpeed = (tarAngle - curAngle) / times;
            }


            if (stepDist <= dist) {
                // 计算位移
//                System.out.println("moved");
                walkDir.mult(stepDist, step);
                loc.addLocal(step);

                Vector3f next = new Vector3f(loc.x, loc.y, this.z);
                spatial.setLocalTranslation(next);
//                System.out.printf("name: %s, x: %f, y: %f\n",spatial.getName(),next.x,next.y);
                spatial.rotate(0, 0, rotateSpeed);

            } else {
                // 可以到达目标点
//                System.out.println("reach");
                walkDir = null;
                rotateDir = 0;
                spatial.setLocalTranslation(target.x, target.y, this.z);
                curAngle = spatial.getLocalRotation().getZ() *2;
                spatial.rotate(0, 0, tarAngle - curAngle);
                target = null;
                this.spatial.removeControl(this);
            }

        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}