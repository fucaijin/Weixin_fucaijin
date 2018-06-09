package com.fucaijin.weixin_fucaijin.data;

import com.github.promeg.pinyinhelper.Pinyin;

/**
 * Created by fucaijin on 2018/6/3.
 */

public class AddressListItemData implements Comparable<AddressListItemData> {
    private int headSculpture;
    private String nickName;
    private String nickNameFirstLetter;
    private boolean isMan;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
        this.nickNameFirstLetter = String.valueOf(Pinyin.toPinyin(nickName.charAt(0)).charAt(0));
        //截取昵称首个字符，并转化为拼音，然后再截取首个拼音，再转化为String，然后赋值给nickNameFirstLetter
    }

    public int getHeadSculpture() {
        return headSculpture;
    }

    public String getNickNameFirstLetter() {
        return nickNameFirstLetter;
    }

    public void setHeadSculpture(int headSculpture) {
        this.headSculpture = headSculpture;
    }

    public boolean isMan() {
        return isMan;
    }

    public void setMan(boolean man) {
        isMan = man;
    }

    @Override
    public int compareTo(AddressListItemData addressListItemData) {
        return nickNameFirstLetter.compareTo(addressListItemData.nickNameFirstLetter);
    }
}
