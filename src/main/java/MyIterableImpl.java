import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class MyIterableImpl implements MyIterable<Integer> {
    List<Integer> list;
    private static MyIterableImpl myIterable = null;

    private MyIterableImpl() {
    }

    private static MyIterableImpl getInstance() {
        if (myIterable == null)
            myIterable = new MyIterableImpl();

        return myIterable;
    }

    public static MyIterable<Integer> of(int... i) {
        getInstance().list = new ArrayList<Integer>();

        for (int anI : i) {
            getInstance().list.add(anI);
        }

        return getInstance();
    }


    @Override
    public MyIterable<Integer> filter(@Nonnull Predicate<Integer> predicate) {
        List<Integer> tmpList = new ArrayList<Integer>();
        for (Integer integer : list) {
            if (predicate.apply(integer))
                tmpList.add(integer);
        }
        list = new ArrayList<Integer>(tmpList);
        return getInstance();
    }

    @Override
    public <T> MyIterable<T> transform(@Nonnull Function<Integer, T> transformer) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, (Integer) transformer.apply(list.get(i)));
        }

        return (MyIterable<T>) getInstance();
    }

    @Override
    public <T> T aggregate(@Nullable T initValue, @Nonnull Aggregator<Integer, T> aggregator) {
        Integer result = (Integer) initValue;
        for (Integer integer : list) {
            result = (Integer) aggregator.apply((T) result, integer);
        }
        return (T) result;
    }

    @Override
    public SortedSet<Integer> toSet(@Nonnull Comparator<Integer> comarator) {
        return null;
    }

    @Override
    public List<Integer> toList() {
        return null;
    }

    @Nullable
    @Override
    public Integer findFirst(@Nonnull Predicate<Integer> predicate) {
        for (Integer integer : list) {
            if (predicate.apply(integer))
                return integer;
        }
        return 0;
    }

    @Override
    public Iterator<Integer> iterator() {
        return null;
    }
}
