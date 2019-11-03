package bdata.cap.com.ch3;

import bdata.cap.com.util.Debug;

public class ClassLazyInitDemo {
  public static void main(String[] args) {
    Debug.info(Collaborator.class.hashCode());// 语句①
    Debug.info(Collaborator.number);// 语句②
    Debug.info(Collaborator.flag);

  }

  static class Collaborator {
    static int number = 1;
    static boolean flag = true;
    static {
      Debug.info("Collaborator initializing...");
    }
  }
}
