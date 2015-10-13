package HashingTechnique;

import java.util.ArrayList;
import java.util.Collections;

import Interfaing.HashTable;

public class PseudoRandomProbing<K, V> implements HashTable<K, V> {
	private Pair<K, V> pseudo[];
	private int random[];
	private int numberOfElement;
	private int size;
	private boolean freeplace[];
	private int mod, integerkey, step, index;
	private ArrayList<Pair<K, V>> temp;
	private ArrayList<K> ilterator;
	private int collision;
	private double rehash;

	public PseudoRandomProbing() {
		collision = 0;
		size = 4;
		mod = 1 << size;
		numberOfElement = 0;
		pseudo = new Pair[1 << size];
		random = new int[1 << size];
		freeplace = new boolean[1 << size];
		genereteRandom();
	}

	public int sizeOfArray() {
		return mod;
	}

	public int collis() {
		return collision;
	}

	public void rehashing() {
		temp = new ArrayList();

		for (int i = 0; i < mod; i++) {
			if (freeplace[i]) {
				temp.add(new Pair(pseudo[i].key, pseudo[i].value));

			}
		}
		collision = 0;
		size++;
		mod = 1 << size;
		pseudo = new Pair[mod];
		freeplace = new boolean[mod];
		random = new int[mod];
		numberOfElement = 0;
		genereteRandom();
		for (int i = 0; i < temp.size(); i++) {

			put(temp.get(i).key, temp.get(i).value);

		}

	}

	public void genereteRandom() {
		ArrayList<Integer> dataList = new ArrayList<Integer>();
		for (int i = 0; i < mod; i++) {
			dataList.add(i);
		}

		Collections.shuffle(dataList);

		for (int i = 0; i < mod; i++) {
			random[i] = dataList.get(i);
		}
	}

	@Override
	public void put(K key, V value) {
		delete(key);
		integerkey = Math.abs(key.hashCode() % mod);
		step = 0;
		index = integerkey;
		while (freeplace[index]) {
			collision++;
			index = (integerkey % mod + random[step] % mod) % mod;
			step++;
		}
		pseudo[index] = new Pair(key, value);
		freeplace[index] = true;
		numberOfElement++;
		rehash = (double) numberOfElement / (double) mod;
		if (rehash > 0.75)
			rehashing();

	}

	@Override
	public V get(K key) {
		integerkey = Math.abs(key.hashCode() % mod);
		step = 0;
		index = integerkey;
		while (freeplace[index]) {
			if (pseudo[index].key.equals(key))
				return pseudo[index].value;
			if (step == mod)
				break;
			index = (integerkey + random[step]) % mod;
			step++;

		}

		return null;
	}

	@Override
	public void delete(K key) {
		integerkey = Math.abs(key.hashCode() % mod);
		step = 0;
		index = integerkey;
		while (freeplace[index]) {
			if (pseudo[index].key.equals(key)) {
				freeplace[index] = false;
				numberOfElement--;
				break;
			}
			if (step == mod)
				break;
			index = (integerkey + random[step]) % mod;
			step++;

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
		for (int i = 0; i < mod; i++) {
			if (freeplace[i])
				ilterator.add(pseudo[i].key);

		}
		return ilterator;
	}

}
