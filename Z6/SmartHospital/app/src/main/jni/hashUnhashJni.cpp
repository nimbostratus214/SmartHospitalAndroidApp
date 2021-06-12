//
// Created by Radenko on 6/5/2021.
//

#include "radenko_mihajlovic_smarthospital_JNIExample.h"

JNIEXPORT jint JNICALL Java_radenko_mihajlovic_smarthospital_JNIExample_hashPassword
  (JNIEnv * env, jobject jobj, jint passToHash, jint hashKey){

        return (passToHash xor hashKey);
  }

  JNIEXPORT jint JNICALL Java_radenko_mihajlovic_smarthospital_JNIExample_unhashPassword
    (JNIEnv * env, jobject jobj, jint unhashKey, jint hashedPass){

        return (hashedPass xor unhashKey);

    }