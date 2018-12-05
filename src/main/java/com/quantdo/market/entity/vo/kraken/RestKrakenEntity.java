package com.quantdo.market.entity.vo.kraken;

import java.util.List;

public class RestKrakenEntity{
    private List<String> a;

    private List<String> b;

    private List<String> c;

    private List<String> v;

    private List<String> p;

    private List<Integer> t;

    private List<String> l;

    private List<String> h;

    private String o;

    public void setA(List<String> a){
        this.a = a;
    }
    public List<String> getA(){
        return this.a;
    }
    public void setB(List<String> b){
        this.b = b;
    }
    public List<String> getB(){
        return this.b;
    }
    public void setC(List<String> c){
        this.c = c;
    }
    public List<String> getC(){
        return this.c;
    }
    public void setV(List<String> v){
        this.v = v;
    }
    public List<String> getV(){
        return this.v;
    }
    public void setP(List<String> p){
        this.p = p;
    }
    public List<String> getP(){
        return this.p;
    }
    public void setT(List<Integer> t){
        this.t = t;
    }
    public List<Integer> getT(){
        return this.t;
    }
    public void setL(List<String> l){
        this.l = l;
    }
    public List<String> getL(){
        return this.l;
    }
    public void setH(List<String> h){
        this.h = h;
    }
    public List<String> getH(){
        return this.h;
    }
    public void setO(String o){
        this.o = o;
    }
    public String getO(){
        return this.o;
    }
	@Override
	public String toString() {
		return "KrakenEntity [a=" + a + ", b=" + b + ", c=" + c + ", v=" + v + ", p=" + p + ", t=" + t + ", l=" + l
				+ ", h=" + h + ", o=" + o + "]";
	}
    
}

