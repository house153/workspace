package com.house.variety.seq;

import com.google.common.base.Preconditions;
import com.house.variety.util.StringUtils;

import java.security.SecureRandom;
import java.util.Calendar;

/**
 * @Auther: tanfan
 * @Date: 2019/5/8 14:05
 * @Description:
 */
public class CommonSelfIdGenerator implements IdGenerator {
    public static final long SJDBC_EPOCH;
    private static final long SEQUENCE_BITS = 12L;
    private static final long WORKER_ID_BITS = 10L;
    private static final long SEQUENCE_MASK = 4095L;
    private static final long WORKER_ID_LEFT_SHIFT_BITS = 12L;
    private static final long TIMESTAMP_LEFT_SHIFT_BITS = 22L;
    private static final long WORKER_ID_MAX_VALUE = 1024L;
    private static AbstractClock clock = AbstractClock.systemClock();
    private static long workerId;
    private long sequence;
    private long lastTime;

    public CommonSelfIdGenerator() {
    }

    public static void setClock(AbstractClock clock) {
        clock = clock;
    }

    public static long getWorkerId() {
        return workerId;
    }

    static void initWorkerId() {
        String workerId = System.getProperty("sjdbc.self.id.generator.worker.id");
        if (StringUtils.isNotEmpty(workerId)) {
            setWorkerId(Long.valueOf(workerId));
        } else {
            workerId = System.getenv("SJDBC_SELF_ID_GENERATOR_WORKER_ID");
            if (!StringUtils.isEmpty(workerId)) {
                setWorkerId(Long.valueOf(workerId));
            }
        }
    }

    public static void setWorkerId(Long workerId) {
        Preconditions.checkArgument(workerId >= 0L && workerId < 1024L);
        workerId = workerId;
    }

    public static long getWorkerIdLength() {
        return 10L;
    }

    public synchronized Number generateId() {
        long time = clock.millis();
        Preconditions.checkState(this.lastTime <= time, "Clock is moving backwards, last time is %d milliseconds, current time is %d milliseconds", this.lastTime, time);
        if (this.lastTime == time) {
            if (0L == (++this.sequence & 4095L)) {
                time = this.waitUntilNextTime(time);
            }
        } else {
            this.sequence = (long) (new SecureRandom()).nextInt(10);
        }

        this.lastTime = time;
        return time - SJDBC_EPOCH << 22 | workerId << 12 | this.sequence;
    }

    private long waitUntilNextTime(long lastTime) {
        long time;
        for (time = clock.millis(); time <= lastTime; time = clock.millis()) {
            ;
        }

        return time;
    }

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 10, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        SJDBC_EPOCH = calendar.getTimeInMillis();
        initWorkerId();
    }
}
