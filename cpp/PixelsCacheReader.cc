/*
 * Copyright 2019 PixelsDB.
 *
 * This file is part of Pixels.
 *
 * Pixels is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * Pixels is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Affero GNU General Public License for more details.
 *
 * You should have received a copy of the Affero GNU General Public
 * License along with Foobar.  If not, see
 * <https://www.gnu.org/licenses/>.
 */
#include <iostream>
#include "PixelsCacheReader.h"
#include "MemoryMappedFile.h"
using namespace std;

JNIEXPORT jbyteArray JNICALL Java_io_pixelsdb_pixels_cache_NativePixelsCacheReader_get
  (JNIEnv *env, jclass cls, jlong blockId, jshort rowGroupId, jshort columnId)
{
    jbyteArray res;
    return res;
}


JNIEXPORT jbyteArray JNICALL Java_io_pixelsdb_pixels_cache_NativePixelsCacheReader_sch
  (JNIEnv *env, jclass cls, jlong blockId, jshort rowGroupId, jshort columnId)
{
    jbyteArray res;
    return res;
}