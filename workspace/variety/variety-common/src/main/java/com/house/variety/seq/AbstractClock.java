package com.house.variety.seq;

/**
 * @Auther: tanfan
 * @Date: 2019/5/8 14:05
 * @Description:
 */
public abstract class AbstractClock {
    public AbstractClock() {
    }

    public static AbstractClock systemClock() {
        return new AbstractClock.SystemClock();
    }

    public abstract long millis();

    private static final class SystemClock extends AbstractClock {
        private SystemClock() {
        }

        public long millis() {
            return System.currentTimeMillis();
        }
    }
}
