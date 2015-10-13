package HashingTechnique;

import java.util.ArrayList;

import Interfaing.HashTable;

public class LinearProbing<K, V> implements HashTable<K, V> {

	private Pair<K, V> linearProbing[];
	private int numberOfElement;
	private boolean checkFree[];
	private ArrayList<K> ilterator;

	private int mod;
	private int size;
	private int step;
	private ArrayList<Pair<K, V>> temp;

	private int integerkey, begin;
	private double rehash;
	private int collision;

	// step 3
	public LinearProbing() {
		collision = 0;
		numberOfElement = 0;
		size = 4;
		step = 3;
		mod = 1 << size;
		linearProbing = new Pair[1 << size];
		checkFree = new boolean[1 << size];

		// inialize the size of array 2^4
	}

	public int sizeOfArray() {
		return mod;
	}

	public int collis() {
		return collision;
	}

	public void rehashing() {
		temp = new ArrayList();

		for (int i = 0; i < 1 << size; i++) {
			if (checkFree[i]) {
				temp.add(new Pair(linearProbing[i].key, linearProbing[i].value));

			}
		}
		size++;
		mod = 1 << size;
		numberOfElement = 0;
		collision = 0;
		linearProbing = new Pair[1 << size];
		checkFree = new boolean[1 << size];
		for (int i = 0; i < temp.size(); i++) {

			put(temp.get(i).key, temp.get(i).value);

		}

	}

	@Override
	public void put(K key, V value) {
		delete(key);
		integerkey = Math.abs(key.hashCode() % mod);

		while (checkFree[integerkey]) {
			collision++;
			integerkey = (integerkey % mod + step % mod) % mod;
		}
		linearProbing[integerkey] = new Pair(key, value);
		checkFree[integerkey] = true;

		numberOfElement++;
		rehash = (double) numberOfElement / (double) mod;
		if (rehash > 0.75)
			rehashing();

	}

	@Override
	public V get(K key) {
		integerkey = Math.abs(key.hashCode() % mod);
		begin = integerkey;
		while (checkFree[begin] && !linearProbing[begin].key.equals(key)) {
			begin = (begin % mod + step % mod) % mod;
			if (begin == integerkey)
				break;
		}
		if (checkFree[begin] && linearProbing[begin].key.equals(key))
			return linearProbing[begin].value;
		return null;
	}

	@Override
	public void delete(K key) {
		integerkey = Math.abs(key.hashCode() % mod);
		begin = integerkey;
		while (checkFree[begin] && !linearProbing[begin].key.equals(key)) {
			begin = (begin % mod + step % mod) % mod;
			if (begin == integerkey)
				break;
		}
		if (checkFree[begin] && linearProbing[begin].key.equals(key)) {
			checkFree[begin] = false;
			numberOfElement--;
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
		if (numberOfElement == 0)
			return true;
		return false;
	}

	@Override
	public int size() {
		return numberOfElement;
	}

	@Override
	public Iterable<K> keys() {
		ilterator = new ArrayList();
		for (int i = 0; i < 1 << size; i++) {
			if (checkFree[i])
				ilterator.add(linearProbing[i].key);

		}
		return ilterator;
	}
}
