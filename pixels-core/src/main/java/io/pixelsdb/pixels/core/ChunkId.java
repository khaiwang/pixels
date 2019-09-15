/*
 * Copyright 2017-2019 PixelsDB.
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
package io.pixelsdb.pixels.core;

/**
 * pixels
 *
 * @author guodong
 */
public class ChunkId
{
    private final int rowGroupId;
    private final int columnId;
    private final long offset;
    private final long length;

    public ChunkId(int rowGroupId, int columnId, long offset, long length)
    {
        this.rowGroupId = rowGroupId;
        this.columnId = columnId;
        this.offset = offset;
        this.length = length;
    }

    public int getRowGroupId()
    {
        return rowGroupId;
    }

    public int getColumnId()
    {
        return columnId;
    }

    public long getOffset()
    {
        return offset;
    }

    public long getLength()
    {
        return length;
    }
}