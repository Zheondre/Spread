package com.mygdx.game;

import java.util.ArrayList;

public class LevelInfo {

    byte levelNum;
    byte peopleNum;
    byte securityNum;
    byte copNum;
    byte emtNum;

    int BackUpTime;

    byte healthPackNum;
    byte antidoes;
    byte cureZones;

    public LevelInfo(byte levelNum, byte peopleNum, byte securityNum, byte copNum, byte emtNum) {
        this.levelNum = levelNum;
        this.peopleNum = peopleNum;
        this.securityNum = securityNum;
        this.copNum = copNum;
        this.emtNum = emtNum;
        this.BackUpTime = 120;
    }

    public LevelInfo(byte levelNum, byte peopleNum, byte securityNum, byte copNum, byte emtNum, int backUpTime) {
        this.levelNum = levelNum;
        this.peopleNum = peopleNum;
        this.securityNum = securityNum;
        this.copNum = copNum;
        this.emtNum = emtNum;
        this.BackUpTime = backUpTime;
    }

    public LevelInfo(byte levelNum, byte peopleNum, byte securityNum, byte copNum, byte emtNum, int backUpTime, byte healthPackNum, byte antidoes, byte cureZones) {
        this.levelNum = levelNum;
        this.peopleNum = peopleNum;
        this.securityNum = securityNum;
        this.copNum = copNum;
        this.emtNum = emtNum;
        this.BackUpTime = backUpTime;
        this.healthPackNum = healthPackNum;
        this.antidoes = antidoes;
        this.cureZones = cureZones;
    }

    //not sure what else to add
    public int getBackUpTime() {
        return BackUpTime;
    }

    public byte getLevelNum() {
        return levelNum;
    }

    public byte getPeopleNum() {
        return peopleNum;
    }

    public byte getSecurityNum() {
        return securityNum;
    }

    public byte getCopNum() {
        return copNum;
    }

    public byte getEmtNum() {
        return emtNum;
    }

    public byte getHealthPackNum() {
        return healthPackNum;
    }

    public byte getAntidoes() {
        return antidoes;
    }

    public byte getCureZones() {
        return cureZones;
    }
}
