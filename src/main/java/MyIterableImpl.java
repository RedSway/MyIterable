import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class MyIterableImpl implements MyIterable<Integer> {
    private List<Integer> list;
    private Predicate predicate;
    private Function transform;
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
        if (getInstance().predicate == null) {
            this.predicate = predicate;
        } else {
            List<Integer> tmpList = new ArrayList<Integer>();

            for (Integer integer : getInstance().list) {
                if (predicate.apply(integer))
                    tmpList.add(integer);
            }

            getInstance().list = new ArrayList<Integer>(tmpList);
        }

        return getInstance();
    }

    @Override
    public <T> MyIterable<T> transform(@Nonnull Function<Integer, T> transformer) {
        if (getInstance().transform == null) {
            this.transform = transformer;
        } else {
            for (int i = 0; i < getInstance().list.size(); i++) {
                getInstance().list.set(i, (Integer) transformer.apply(getInstance().list.get(i)));
            }
        }
        return (MyIterable<T>) getInstance();
    }

    @Override
    public <T> T aggregate(@Nullable T initValue, @Nonnull Aggregator<Integer, T> aggregator) {
        getInstance()
                .filter(predicate)
                .transform(transform);
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
        Iterator<Integer> iterator = getInstance().iterator();

        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (this.predicate.apply(next)) {
                next = (Integer) this.transform.apply(next);
                if (predicate.apply(next))
                    return next;
            }

        }
        return 0;
    }

    @Override
    public Iterator<Integer> iterator() {
        return list.iterator();
    }
}
