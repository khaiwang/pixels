/*
 * Copyright 2023 PixelsDB.
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
 * License along with Pixels.  If not, see
 * <https://www.gnu.org/licenses/>.
 */
package io.pixelsdb.pixels.amphi;

import java.lang.reflect.Field;

public abstract class TpchQuery
{
    public static final String Q1 = "select l_returnflag, l_linestatus, sum(l_quantity) as sum_qty, sum(l_extendedprice) as sum_base_price, sum(l_extendedprice *(1 - l_discount)) as sum_disc_price, sum(l_extendedprice * (1 - l_discount) * (1 + l_tax)) as sum_charge, avg(l_quantity) as avg_qty, avg(l_extendedprice) as avg_price, avg(l_discount) as avg_disc, count(*) as count_order from LINEITEM where l_shipdate <= date '1998-12-01' - interval '108' day(3) group by l_returnflag, l_linestatus order by l_returnflag, l_linestatus";
    public static final String Q2 = "select s_acctbal, s_name, n_name, p_partkey, p_mfgr, s_address, s_phone, s_comment from PART, SUPPLIER, PARTSUPP, NATION, REGION where p_partkey = ps_partkey and s_suppkey = ps_suppkey and p_size = 30 and p_type like '%STEEL' and s_nationkey = n_nationkey and n_regionkey = r_regionkey and r_name = 'ASIA' and ps_supplycost =( select min(ps_supplycost) from PARTSUPP, SUPPLIER, NATION, REGION where p_partkey = ps_partkey and s_suppkey = ps_suppkey and s_nationkey = n_nationkey and n_regionkey = r_regionkey and r_name = 'ASIA') order by s_acctbal desc, n_name, s_name, p_partkey limit 100";
    public static final String Q3 = "select l_orderkey, sum(l_extendedprice *(1 - l_discount)) as revenue, o_orderdate, o_shippriority from CUSTOMER, ORDERS, LINEITEM where c_mktsegment = 'AUTOMOBILE' and c_custkey = o_custkey and l_orderkey = o_orderkey and o_orderdate < '1995-03-13' and l_shipdate > '1995-03-13' group by l_orderkey, o_orderdate, o_shippriority order by revenue desc, o_orderdate limit 10";
    public static final String Q4 = "select o_orderpriority, count(*) as order_count from ORDERS where o_orderdate >= '1995-01-01' and o_orderdate < date '1995-01-01' + interval '3' month and exists( select * from LINEITEM where l_orderkey = o_orderkey and l_commitdate < l_receiptdate) group by o_orderpriority order by o_orderpriority";
    public static final String Q5 = "select n_name, sum(l_extendedprice *(1 - l_discount)) as revenue from CUSTOMER, ORDERS, LINEITEM, SUPPLIER, NATION, REGION where c_custkey = o_custkey and l_orderkey = o_orderkey and l_suppkey = s_suppkey and c_nationkey = s_nationkey and s_nationkey = n_nationkey and n_regionkey = r_regionkey and r_name = 'MIDDLE EAST' and o_orderdate >= date '1994-01-01' and o_orderdate < date '1994-01-01' + interval 1 year group by n_name order by revenue desc";
    public static final String Q6 = "select sum(l_extendedprice * l_discount) as revenue from LINEITEM where l_shipdate >= '1994-01-01' and l_shipdate < date '1994-01-01' + interval '1' year and l_discount between 0.06 - 0.01 and 0.06 + 0.01 and l_quantity < 24";
    public static final String Q7 = "select supp_nation, cust_nation, l_year, sum(volume) as revenue from( select n1.n_name as supp_nation, n2.n_name as cust_nation, extract( year from l_shipdate) as l_year, l_extendedprice * (1 - l_discount) as volume from SUPPLIER, LINEITEM, ORDERS, CUSTOMER, NATION n1, NATION n2 where s_suppkey = l_suppkey and o_orderkey = l_orderkey and c_custkey = o_custkey and s_nationkey = n1.n_nationkey and c_nationkey = n2.n_nationkey and ( ( n1.n_name = 'JAPAN' and n2.n_name = 'INDIA' ) or ( n1.n_name = 'INDIA' and n2.n_name = 'JAPAN' ) ) and l_shipdate between '1995-01-01' and '1996-12-31' ) as shipping group by supp_nation, cust_nation, l_year order by supp_nation, cust_nation, l_year";
    public static final String Q8 = "select o_year, sum( case when nation = 'INDIA' then volume else 0 end) / sum(volume) as mkt_share from( select extract( year from o_orderdate ) as o_year, l_extendedprice * (1 - l_discount) as volume, n2.n_name as nation from PART, SUPPLIER, LINEITEM, ORDERS, CUSTOMER, NATION n1, NATION n2, REGION where p_partkey = l_partkey and s_suppkey = l_suppkey and l_orderkey = o_orderkey and o_custkey = c_custkey and c_nationkey = n1.n_nationkey and n1.n_regionkey = r_regionkey and r_name = 'ASIA' and s_nationkey = n2.n_nationkey and o_orderdate between '1995-01-01' and '1996-12-31' and p_type = 'SMALL PLATED COPPER' ) as all_nations group by o_year order by o_year";
    public static final String Q9 = "select nation, o_year, sum(amount) as sum_profit from( select n_name as nation, extract(year from o_orderdate) as o_year, l_extendedprice * (1 - l_discount) - ps_supplycost * l_quantity as amount from PART, SUPPLIER, LINEITEM, PARTSUPP, ORDERS, NATION where s_suppkey = l_suppkey and ps_suppkey = l_suppkey and ps_partkey = l_partkey and p_partkey = l_partkey and o_orderkey = l_orderkey and s_nationkey = n_nationkey and p_name like '%dim%') as profit group by nation, o_year order by nation, o_year desc";
    public static final String Q10 = "select c_custkey, c_name, sum(l_extendedprice * (1 - l_discount)) as revenue, c_acctbal, n_name, c_address, c_phone, c_comment from CUSTOMER, ORDERS, LINEITEM, NATION where c_custkey = o_custkey and l_orderkey = o_orderkey and o_orderdate >= '1993-08-01' and o_orderdate < date '1993-08-01' + interval '3' month and l_returnflag = 'R' and c_nationkey = n_nationkey group by c_custkey, c_name, c_acctbal, c_phone, n_name, c_address, c_comment order by revenue desc limit 20";
    public static final String Q11 = "select ps_partkey, sum(ps_supplycost * ps_availqty) as val from PARTSUPP, SUPPLIER, NATION where ps_suppkey = s_suppkey and s_nationkey = n_nationkey and n_name = 'MOZAMBIQUE' group by ps_partkey having sum(ps_supplycost * ps_availqty) >( select sum(ps_supplycost * ps_availqty) * 0.0001000000 from PARTSUPP, SUPPLIER, NATION where ps_suppkey = s_suppkey and s_nationkey = n_nationkey and n_name = 'MOZAMBIQUE') order by val desc";
    public static final String Q12 = "select l_shipmode, sum( case when o_orderpriority = '1-URGENT' or o_orderpriority = '2-HIGH' then 1 else 0 end ) as high_line_count, sum( case when o_orderpriority <> '1-URGENT' and o_orderpriority <> '2-HIGH' then 1 else 0 end ) as low_line_count from ORDERS, LINEITEM where o_orderkey = l_orderkey and l_shipmode in ('RAIL', 'FOB') and l_commitdate < l_receiptdate and l_shipdate < l_commitdate and l_receiptdate >= '1997-01-01' and l_receiptdate < date '1997-01-01' + interval '1' year group by l_shipmode order by l_shipmode";
    public static final String Q13 = "select c_count, count(*) as custdist from ( select c_custkey, count(o_orderkey) as c_count from CUSTOMER left outer join ORDERS on c_custkey = o_custkey and o_comment not like '%pending%deposits%' group by c_custkey ) c_orders group by c_count order by custdist desc, c_count desc";
    public static final String Q14 = "select 100.00 * sum( case when p_type like 'PROMO%' then l_extendedprice * (1 - l_discount) else 0 end ) / sum(l_extendedprice * (1 - l_discount)) as promo_revenue from LINEITEM, PART where l_partkey = p_partkey and l_shipdate >= '1996-12-01' and l_shipdate < date '1996-12-01' + interval '1' month";
    public static final String Q15 = "select s_suppkey, s_name, s_address, s_phone, total_revenue from SUPPLIER, ( select l_suppkey AS supplier_no, sum(l_extendedprice * (1 - l_discount)) AS total_revenue from LINEITEM where l_shipdate >= '1997-07-01' and l_shipdate < date '1997-07-01' + interval '3' month group by l_suppkey ) REVENUE0 where s_suppkey = supplier_no and total_revenue = ( select max(total_revenue) from ( select l_suppkey AS supplier_no, sum(l_extendedprice * (1 - l_discount)) AS total_revenue from LINEITEM where l_shipdate >= '1997-07-01' and l_shipdate < date '1997-07-01' + interval '3' month group by l_suppkey ) REVENUE0 ) order by s_suppkey";
    public static final String Q16 = "select p_brand, p_type, p_size, count(distinct ps_suppkey) as supplier_cnt from PARTSUPP, PART where p_partkey = ps_partkey and p_brand <> 'Brand#34' and p_type not like 'LARGE BRUSHED%' and p_size in (48, 19, 12, 4, 41, 7, 21, 39) and ps_suppkey not in ( select s_suppkey from SUPPLIER where s_comment like '%Customer%Complaints%' ) group by p_brand, p_type, p_size order by supplier_cnt desc, p_brand, p_type, p_size";
    public static final String Q17 = "select sum(l_extendedprice) / 7.0 as avg_yearly from LINEITEM, PART where p_partkey = l_partkey and p_brand = 'Brand#44' and p_container = 'WRAP PKG' and l_quantity < ( select 0.2 * avg(l_quantity) from LINEITEM where l_partkey = p_partkey )";
    public static final String Q18 = "select c_name, c_custkey, o_orderkey, o_orderdate, o_totalprice, sum(l_quantity) from CUSTOMER, ORDERS, LINEITEM where o_orderkey in ( select l_orderkey from LINEITEM group by l_orderkey having sum(l_quantity) > 314 ) and c_custkey = o_custkey and o_orderkey = l_orderkey group by c_name, c_custkey, o_orderkey, o_orderdate, o_totalprice order by o_totalprice desc, o_orderdate limit 100";
    public static final String Q19 = "select sum(l_extendedprice *(1 - l_discount)) as revenue from LINEITEM, PART where ( p_partkey = l_partkey and p_brand = 'Brand#52' and p_container in ('SM CASE', 'SM BOX', 'SM PACK', 'SM PKG') and l_quantity >= 4 and l_quantity <= 4 + 10 and p_size between 1 and 5 and l_shipmode in ('AIR', 'AIR REG') and l_shipinstruct = 'DELIVER IN PERSON') or ( p_partkey = l_partkey and p_brand = 'Brand#11' and p_container in ('MED BAG', 'MED BOX', 'MED PKG', 'MED PACK') and l_quantity >= 18 and l_quantity <= 18 + 10 and p_size between 1 and 10 and l_shipmode in ('AIR', 'AIR REG') and l_shipinstruct = 'DELIVER IN PERSON' ) or ( p_partkey = l_partkey and p_brand = 'Brand#51' and p_container in ('LG CASE', 'LG BOX', 'LG PACK', 'LG PKG') and l_quantity >= 29 and l_quantity <= 29 + 10 and p_size between 1 and 15 and l_shipmode in ('AIR', 'AIR REG') and l_shipinstruct = 'DELIVER IN PERSON' )";
    public static final String Q20 = "select s_name, s_address from SUPPLIER, NATION where s_suppkey in ( select ps_suppkey from PARTSUPP where ps_partkey in ( select p_partkey from PART where p_name like 'green%' ) and ps_availqty > ( select 0.5 * sum(l_quantity) from LINEITEM where l_partkey = ps_partkey and l_suppkey = ps_suppkey and l_shipdate >= '1993-01-01' and l_shipdate < date '1993-01-01' + interval '1' year ) ) and s_nationkey = n_nationkey and n_name = 'ALGERIA' order by s_name";
    public static final String Q21 = "select s_name, count(*) as numwait from SUPPLIER, LINEITEM l1, ORDERS, NATION where s_suppkey = l1.l_suppkey and o_orderkey = l1.l_orderkey and o_orderstatus = 'F' and l1.l_receiptdate > l1.l_commitdate and exists ( select * from LINEITEM l2 where l2.l_orderkey = l1.l_orderkey and l2.l_suppkey <> l1.l_suppkey ) and not exists ( select * from LINEITEM l3 where l3.l_orderkey = l1.l_orderkey and l3.l_suppkey <> l1.l_suppkey and l3.l_receiptdate > l3.l_commitdate ) and s_nationkey = n_nationkey and n_name = 'EGYPT' group by s_name order by numwait desc, s_name limit 100";
    public static final String Q22 = "select cntrycode, count(*) as numcust, sum(c_acctbal) as totacctbal from ( select substring( c_phone from 1 for 2 ) as cntrycode, c_acctbal from CUSTOMER where substring( c_phone from 1 for 2 ) in ('20', '40', '22', '30', '39', '42', '21') and c_acctbal > ( select avg(c_acctbal) from CUSTOMER where c_acctbal > 0.00 and substring( c_phone from 1 for 2 ) in ('20', '40', '22', '30', '39', '42', '21') ) and not exists ( select * from ORDERS where o_custkey = c_custkey ) ) as custsale group by cntrycode order by cntrycode";

    public static String getQuery(int i) throws NoSuchFieldException, IllegalAccessException
    {
        Field field = TpchQuery.class.getDeclaredField("Q" + i);
        Object value = field.get(null);
        return value.toString();
    }
}