package HashingTechnique;

import java.util.ArrayList;

import Interfaing.HashTable;

public class Bucketing<K, V> implements HashTable<K, V> {

	private Pair<K, V> bucketHash[];
	private ArrayList<Pair<K, V>> overflow;
	private int sizeBucket[];
	private int numberOfElements;
	private int index, integerkey;
	private int size;
	private int mod, numberOfSlots;
	private ArrayList<K> ilterator;
	double rehash;

	// mod 10
	// 10 bucket
	public Bucketing() {
		size = 100;
		mod = 10;
		numberOfSlots = 10;
		bucketHash = new Pair[size];
		overflow = new ArrayList();
		sizeBucket = new int[10];
		numberOfElements = 0;

	}

	public int sizeOfArray() {
		return size;
	}

	public void rehashng() {

		ArrayList<Pair<K, V>> temp1 = new ArrayList();

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < sizeBucket[i]; j++) {

				int index = i * numberOfSlots + j;
				temp1.add(new Pair(bucketHash[index].key,
						bucketHash[index].value));
			}
		}
		for (int i = 0; i < overflow.size(); i++) {
			temp1.add(overflow.get(i));
		}

		size *= 4;
		numberOfSlots *= 2;
		mod *= 2;
		bucketHash = new Pair[size];
		sizeBucket = new int[mod];
		numberOfElements = 0;

		for (int i = 0; i < temp1.size(); i++) {
			put(temp1.get(i).key, temp1.get(i).value);

		}

	}

	@Override
	public void put(K key, V value) {
		delete(key);
		integerkey = Math.abs(key.hashCode() % mod);
		// check if there is a place in buckting array or not
		if (sizeBucket[integerkey] != numberOfSlots) {
			int index = numberOfSlots * integerkey + sizeBucket[integerkey];
			bucketHash[index] = new Pair(key, value);
			sizeBucket[integerkey]++;

		} else {
			overflow.add(new Pair(key, value));
		}
		numberOfElements++;
		rehash = (double) numberOfElements / (double) size;
		if (rehash > 0.75)
			rehashng();

	}

	@Override
	public V get(K key) {
		integerkey = Math.abs(key.hashCode() % mod);
		index = numberOfSlots * integerkey;
		for (int i = index; i < index + sizeBucket[integerkey]; i++) {
			if (bucketHash[i].key.equals(key))
				return bucketHash[i].value;
		}
		if (sizeBucket[integerkey] == numberOfSlots) {
			for (int i = 0; i < overflow.size(); i++) {
				if (overflow.get(i).key.equals(key))
					return overflow.get(i).value;
			}
		}
		return null;
	}

	@Override
	public void delete(K key) {
		integerkey = Math.abs(key.hashCode() % mod);
		index = numberOfSlots * integerkey;
		boolean flag = false;

		for (int i = index; i < index + sizeBucket[integerkey]; i++) {
			if (bucketHash[i].key.equals(key)) {
				flag = true;
			} else if (flag) {
				bucketHash[i - 1] = new Pair(bucketHash[i].key,
						bucketHash[i].value);

			}
		}
		if (flag) {
			numberOfElements--;
			sizeBucket[integerkey]--;
		} else if (sizeBucket[integerkey] == numberOfSlots) {
			for (int i = 0; i < overflow.size(); i++) {
				if (overflow.get(i).key.equals(key)) {
					overflow.remove(i);
					numberOfElements--;
					break;
				}
			}
		}

	}

	@Override
	public boolean contains(K key) {
		V check = get(key);
		if (check == null)
			return false;
		return true;
	}

	@Override
	public boolean isEmpty() {
		if (numberOfElements == 0)
			return true;
		return false;
	}

	@Override
	public int size() {

		return numberOfElements;
	}

	@Override
	public Iterable<K> keys() {
		ilterator = new ArrayList();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < sizeBucket[i]; j++) {
				int index = i * numberOfSlots + j;
				ilterator.add(bucketHash[index].key);

			}
		}
		for (int i = 0; i < overflow.size(); i++) {
			ilterator.add(overflow.get(i).key);

		}
		return ilterator;
	}

}
