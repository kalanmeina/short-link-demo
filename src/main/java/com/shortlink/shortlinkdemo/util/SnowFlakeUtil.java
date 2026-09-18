package com.shortlink.shortlinkdemo.util;

//import sun.security.jca.GetInstance;

public class SnowFlakeUtil {
    private static final long START_TIMESTAMP=1704067200000L;

    private static final long worker_id_bits=5L;
    private static final long datacenter_id_bits=5L;
    private static final long sequence_bits=12L;


    private static final long max_worker_id=~(-1L << worker_id_bits);
    private static final long max_datacenter_id=12L;



    private static final long worker_id_shift=sequence_bits;
    private static final long datacenter_id_shift=sequence_bits+worker_id_bits;
    private static final long TIMESTAMP_shift=sequence_bits+worker_id_bits+datacenter_id_bits;


    private  static final  long sequence_mask=~(-1L << sequence_bits);

    private  static final long workerId=0L;
    private  static final long datacenterId=0L;
    private   long sequence=0L;
    private   long lastTimestamp=-1L;

    private static final SnowFlakeUtil instance=new SnowFlakeUtil();
    public static SnowFlakeUtil getInstance(){
        return instance;
    }

    private SnowFlakeUtil(){}

    public synchronized long nextId(){

        long timestamp=System.currentTimeMillis();
        if(timestamp<lastTimestamp){
            throw new RuntimeException("时钟回拨，拒绝生成ID");
        }
        if(timestamp==lastTimestamp){
            sequence =(sequence+1) & sequence_mask;
            if(sequence==0){
                timestamp=tilNextMillis(lastTimestamp);
            }
        }else{
            sequence=0L;
        }
        lastTimestamp=timestamp;
        return((timestamp-START_TIMESTAMP) << TIMESTAMP_shift)
                | (datacenterId<< datacenter_id_shift)
                |(workerId<<worker_id_shift)
                | sequence;
    }
        private long tilNextMillis(long lastTimestamp){
        long timestamp =System.currentTimeMillis();
        while(timestamp<=lastTimestamp){
            timestamp=System.currentTimeMillis();
        }
        return timestamp;
        }




}
