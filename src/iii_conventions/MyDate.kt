package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int): Comparable<MyDate>  {
    override fun compareTo(other: MyDate): Int {
        val result = 2000*(other.year - this.year) + 31*(other.month - this.month) + other.dayOfMonth - this.dayOfMonth
        return when {
            result == 0 -> 0
            result > 0 -> -1
            else -> 1
        }
    }

    operator fun plus(interval: TimeInterval): MyDate {
        return this.addTimeIntervals(interval, 1)
    }

    operator fun  plus(repeatedTimeInterval: RepeatedTimeInterval): MyDate {
        return  this.addTimeIntervals(repeatedTimeInterval.ti, repeatedTimeInterval.n)
    }

}

operator fun MyDate.rangeTo(other: MyDate): DateRange {
    return DateRange(this, other)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    operator fun times(i: Int): RepeatedTimeInterval {
        return RepeatedTimeInterval(this, i)
    }
}

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

class DateRange(val start: MyDate, val endInclusive: MyDate): Iterator<MyDate> {
    private var currentDate: MyDate = start
    override fun hasNext(): Boolean {
        return currentDate <= endInclusive
    }

    override fun next(): MyDate {
        val result = currentDate
        currentDate = currentDate.nextDay()
        return result
    }

    operator fun  contains(date: MyDate): Boolean {

        if ((date >= start) && (date <= endInclusive)) {
            return true
        }
        return false
    }

}
