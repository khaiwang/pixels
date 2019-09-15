/*
 * Copyright 2018 PixelsDB.
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
package io.pixelsdb.pixels.cache;

import org.junit.Test;

/**
 * Created at: 18-10-24
 * Author: hank
 */
public class TestMemFileConcurrentRW
{
    @Test
    public void testRead()
            throws Exception
    {
        long start = System.nanoTime();
        MemoryMappedFile mem = new MemoryMappedFile("/dev/shm/test", 1024L * 1024L * 256L);
        System.out.println((System.nanoTime() - start) / 1000000.0);
        while (true)
        {
            System.out.println(mem.getLong(16));
            Thread.sleep(1000);
        }
    }

    @Test
    public void testWrite()
            throws Exception
    {
        long start = System.nanoTime();
        MemoryMappedFile mem = new MemoryMappedFile("/dev/shm/test", 1024L * 1024L * 256L);
        System.out.println((System.nanoTime() - start) / 1000000.0);
        while (true)
        {
            mem.getAndAddLong(16, 1);
            Thread.sleep(2000);
        }
    }
}