package com.scollay.neuralnetwork;


public class Geom {
  public static double distance(double x1, double y1, double x2, double y2){
    return Math.sqrt(Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2));
  }
  public static double angle(double x1, double y1, double x2, double y2){
    if(x1 < x2){
      return 90 + Math.toDegrees(Math.atan((y2-y1)/(x2-x1)));
    }
    else{
      return 270 - Math.toDegrees(Math.atan(-(y2-y1)/(x2-x1)));
    }
  }
}
