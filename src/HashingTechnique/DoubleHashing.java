package HashingTechnique;

import java.util.ArrayList;

import Interfaing.HashTable;

public class DoubleHashing<K, V> implements HashTable<K, V> {
	private Pair<K, V> doubleHash[];
	private int size;
	private int mod;
	private int numberOfElement;
	private boolean freeplaces[];
	private int integerkey;
	private int begin, step;
	private ArrayList<Pair<K, V>> temp;
	private ArrayList<K> ilterator;
	private int collision;

	private double rehash;

	public DoubleHashing() {
		collision = 0;
		size = 4;
		mod = 1 << size;
		numberOfElement = 0;
		doubleHash = new Pair[1 << size];
		freeplaces = new boolean[1 << size];
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
			if (freeplaces[i]) {
				temp.add(new Pair(doubleHash[i].key, doubleHash[i].value));

			}
		}
		collision = 0;
		size++;
		mod = 1 << size;
		numberOfElement = 0;
		doubleHash = new Pair[1 << size];
		freeplaces = new boolean[1 << size];
		for (int i = 0; i < temp.size(); i++) {
			put(temp.get(i).key, temp.get(i).value);

		}
	}

	@Override
	public void put(K key, V value) {
		delete(key);
		integerkey = Math.abs(key.hashCode() % mod);
		int step = Math.abs(((key.hashCode() / mod) % (mod / 2))) * 2 + 1;
		while (freeplaces[integerkey]) {
			collision++;
			integerkey = (step % mod + integerkey % mod) % mod;

		}
		doubleHash[integerkey] = new Pair(key, value);
		freeplaces[integerkey] = true;
		numberOfElement++;
		rehash = (double) numberOfElement / (double) mod;
		if (rehash > 0.75)
			rehashing();

	}

	@Override
	public V get(K key) {
		integerkey = Math.abs(key.hashCode() % mod);
		begin = integerkey;
		step = Math.abs(((key.hashCode() / mod) % (mod / 2))) * 2 + 1;
		while (freeplaces[integerkey]) {
			if (doubleHash[integerkey].key.equals(key))
				return doubleHash[integerkey].value;
			integerkey = (integerkey % mod + step % mod) % mod;
			if (begin == integerkey)
				break;
		}
		return null;
	}

	@Override
	public void delete(K key) {
		integerkey = Math.abs(key.hashCode() % mod);
		begin = integerkey;
		step = Math.abs(((key.hashCode() / mod) % (mod / 2))) * 2 + 1;
		while (freeplaces[integerkey]) {
			if (doubleHash[integerkey].key.equals(key)) {
				freeplaces[integerkey] = false;
				numberOfElement--;
				break;
			}
			integerkey = (integerkey % mod + step % mod) % mod;
			if (begin == integerkey)
				break;
		}

	}

	@Override
	public boolean contains(K key) {
		if (get(key) == null)
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
			if (freeplaces[i])
				ilterator.add(doubleHash[i].key);

		}
		return ilterator;
	}

}
