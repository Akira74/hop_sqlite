/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hop.pipeline.transforms.randomvalue;

import java.util.Random;
import javax.crypto.KeyGenerator;
import org.apache.hop.core.row.IRowMeta;
import org.apache.hop.core.util.Uuid4Util;
import org.apache.hop.pipeline.transform.BaseTransformData;
import org.apache.hop.pipeline.transform.ITransformData;

@SuppressWarnings("java:S1104")
public class RandomValueData extends BaseTransformData implements ITransformData {
  boolean readsRows;

  IRowMeta outputRowMeta;

  Uuid4Util u4;
  /* Generating a random Message Authentication Code (MAC MD5) */
  KeyGenerator keyGenHmacMD5;
  /* Generating a random Message Authentication Code (MAC SHA1) */
  KeyGenerator keyGenHmacSHA1;
  /* Generating a random Message Authentication Code (MAC SHA256) */
  KeyGenerator keyGenHmacSHA256;
  /* Generating a random Message Authentication Code (MAC SHA512) */
  KeyGenerator keyGenHmacSHA512;
  /* Generating a random Message Authentication Code (MAC SHA3) */
  KeyGenerator keyGenHmacSHA384;

  public Random randomGenerator;

  public RandomValueData() {
    super();
  }
}
