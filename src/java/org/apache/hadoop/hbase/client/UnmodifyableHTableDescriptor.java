/**
 * Copyright 2008 The Apache Software Foundation
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hbase.client;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.util.Bytes;

class UnmodifyableHTableDescriptor extends HTableDescriptor {
  /*
   * Constructs an empty object.
   * For deserializing an HTableDescriptor instance only.
   */
  UnmodifyableHTableDescriptor() {
    super();
  }

  /*
   * Constructor.
   * @param name Table name.
   * @throws IllegalArgumentException if passed a table name
   * that is made of other than 'word' characters, underscore or period: i.e.
   * <code>[a-zA-Z_0-9.].
   * @see <a href="HADOOP-1581">HADOOP-1581 HBASE: Un-openable tablename bug</a>
   */
  UnmodifyableHTableDescriptor(final String name) {
    this(Bytes.toBytes(name));
  }

  /*
   * Constructor.
   * @param name Table name.
   * @throws IllegalArgumentException if passed a table name
   * that is made of other than 'word' characters, underscore or period: i.e.
   * <code>[a-zA-Z_0-9.].
   * @see <a href="HADOOP-1581">HADOOP-1581 HBASE: Un-openable tablename bug</a>
   */
  UnmodifyableHTableDescriptor(final byte [] name) {
    super(name);
  }
  
  /*
   * Create an unmodifyable copy of an HTableDescriptor
   * @param desc
   */
  UnmodifyableHTableDescriptor(final HTableDescriptor desc) {
    super(desc.getName());
    for (HColumnDescriptor c: desc.getFamilies()) {
      super.addFamily(c);
    }
  }

  /**
   * Does NOT add a column family. This object is immutable
   * @param family HColumnDescriptor of familyto add.
   */
  @Override
  public void addFamily(final HColumnDescriptor family) {
    throw new UnsupportedOperationException("HTableDescriptor is read-only");
  }

  /**
   * @param column
   * @return Column descriptor for the passed family name or the family on
   * passed in column.
   */
  @Override
  public HColumnDescriptor removeFamily(final byte [] column) {
    throw new UnsupportedOperationException("HTableDescriptor is read-only");
  }
}