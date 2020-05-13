package gamesource.battleState.utils.buffs;

import gamesource.battleState.character.Role;
import com.jme3.ui.Picture;
import gamesource.battleState.utils.buffs.foreverBuffs.Artifact;

public class Buff {
    private String name;//buff的名称
    private String description;//buff的文字描述
    private Picture buffPicture;//buff的图片
    private Role role;//buff绑定的人

    public static Buff createBuff(String name, int layer, Role role) {
        switch (name) {
            case "artifact":
                return new Artifact(role, layer);
            default:
                return null;
        }
    }


    public Buff(String name, String description, Picture buffPicture, Role role) {
        this.name = name;
        this.description = description;
        this.buffPicture = buffPicture;
        this.role = role;
    }

    //返回buff名称
    public String getName() {
        return name;
    }

    //返回buff描述
    public String getDescription() {
        return description;
    }

    //返回buff图标
    public Picture getBuffPicture() {
        return buffPicture;
    }

    //返回buff生效的人
    public Role getRole() {
        return role;
    }

    // 获得该buff的时候触发
    public void getFunc() {
    }


    // 触发时候调用,例如回合结束,受到攻击等等
    public void triggerFunc() {
    }


}