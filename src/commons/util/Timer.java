package commons.util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author bailey.fu
 * @date Nov 22, 2010
 * @version 1.3
 * @description
 */
public class Timer {
	/** 加入/丢弃作业 */
	private static final int DO_TYPE_4_TASK = 11;
	/** 加入临时列表 */
	private static final int DO_TYPE_ADD_2_TEMP = 21;
	/** 丢弃临时列表 */
	private static final int DO_TYPE_REMOVE_4_TEMP = 22;

	private static final long MIN_TIMER_PERIOD = 100l;
	private static Timer cTimer = new Timer();

	/** 5秒延迟 */
	private final long TIMER_DELAY = 1000 * 5l;
	/** 间隔,为作业间隔最小值(默认1分钟) */
	private long TIMER_PERIOD = 1000 * 60l;

	private java.util.Timer timer;
	private Collection<TimerTask> taskColl;
	private Collection<TimerTask> tempAdd;
	private Collection<TimerTask> tempRemove;

	private Timer() {
		taskColl = new ArrayList<TimerTask>();
		tempAdd = new ArrayList<TimerTask>();
		tempRemove = new ArrayList<TimerTask>();
	}

	private void run() {
		doTemp(null, DO_TYPE_4_TASK);
		for (TimerTask timerTask : taskColl) {
			try {
				timerTask.doRun();
			} catch (Exception e) {
			}
		}
	}

	private void restart() {
		stop();
		timer = new java.util.Timer();
		timer.schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				try {
					cTimer.run();
				} catch (Exception e) {
				}
			}
		}, TIMER_DELAY, TIMER_PERIOD);
		System.out.println(this + " is starting!");
	}

	private void stop() {
		if (timer != null) {
			try {
				timer.cancel();
			} catch (Exception e) {
			}
			timer = null;
		}
		System.out.println(this + " is stoped!");
	}

	private synchronized void doTemp(TimerTask temp, int DO_TYPE) {
		switch (DO_TYPE) {
		case DO_TYPE_4_TASK: {
			if (tempRemove.size() > 0) {
				for (TimerTask timerTask : tempRemove) {
					taskColl.remove(timerTask);
				}
				tempRemove.clear();
			}
			if (tempAdd.size() > 0) {
				for (TimerTask timerTask : tempAdd) {
					if (!taskColl.contains(timerTask)) {
						taskColl.add(timerTask);
					}
				}
				tempAdd.clear();
			}
		}
			break;
		case DO_TYPE_ADD_2_TEMP: {
			if (temp != null) {
				tempAdd.add(temp);
				long MIN_PERIOD = TIMER_PERIOD;
				for (TimerTask timerTask : tempAdd) {
					if (timerTask.timer_period < MIN_PERIOD) {
						MIN_PERIOD = timerTask.timer_period;
					}
				}
				if (MIN_PERIOD <= 0) {
					MIN_PERIOD = MIN_TIMER_PERIOD;
				}
				if (MIN_PERIOD < TIMER_PERIOD) {
					TIMER_PERIOD = MIN_PERIOD;
				}
			}
		}
			break;

		case DO_TYPE_REMOVE_4_TEMP: {
			if (temp != null) {
				tempRemove.add(temp);
			}
		}
			break;
		}
	}

	public void addTimerTask(TimerTask timerTask) {
		if (timerTask != null) {
			long PRV_TIMER_PERIOD = TIMER_PERIOD;
			doTemp(timerTask, DO_TYPE_ADD_2_TEMP);

			if (TIMER_PERIOD < PRV_TIMER_PERIOD) {
				restart();
			}
		}
	}

	public void removeTimerTask(TimerTask timerTask) {
		doTemp(timerTask, DO_TYPE_REMOVE_4_TEMP);
	}

	public static Timer getInstance() {
		return cTimer;
	}

	public static abstract class TimerTask {
		private long timer_period;
		private double runIndex;

		public TimerTask(long timer_period) {
			this.timer_period = timer_period;
			this.runIndex = 0l;
		}

		private void doRun() throws Exception {
			runIndex++;
			double index = Double.valueOf(timer_period) / cTimer.TIMER_PERIOD;
			if (index == 1 || (runIndex >= index)) {
				run();
				runIndex = 0l;
			}
		}

		abstract public void run() throws Exception;
	}
}
