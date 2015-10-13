package HashingTechnique;

import java.util.ArrayList;

import Interfaing.HashTable;

public class QuadraticProbing<K, V> implements HashTable<K, V> {
	private Pair<K, V> quad[];
	private int numberOfElement;
	private int size;
	private int mod;
	private boolean freePlaces[];
	private int index;
	private ArrayList<Pair<K, V>> temp;
	private ArrayList<K> ilterator;
	private int collision;
	private int place, integerkey;
	private double rehash;

	public QuadraticProbing() {
		collision = 0;
		size = 4;
		mod = 1 << size;
		numberOfElement = 0;
		quad = new Pair[1 << size];
		freePlaces = new boolean[1 << size];
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
			if (freePlaces[i]) {
				temp.add(new Pair(quad[i].key, quad[i].value));

			}
		}
		collision = 0;
		size++;
		mod = 1 << size;
		numberOfElement = 0;
		quad = new Pair[1 << size];
		freePlaces = new boolean[1 << size];

		for (int i = 0; i < temp.size(); i++) {

			put(temp.get(i).key, temp.get(i).value);

		}

	}

	@Override
	public void put(K key, V value) {
		delete(key);
		integerkey = Math.abs(key.hashCode() % mod);

		for (int i = 0; i < (1 << size); i++) {
			place = ((i * (i + 1) / 2) % mod + integerkey % mod) % mod;
			collision++;
			if (!freePlaces[place]) {
				quad[place] = new Pair(key, value);
				numberOfElement++;
				freePlaces[place] = true;

				break;
			}
		}

		rehash = (double) numberOfElement / (double) mod;
		if (rehash > 0.75)
			rehashing();

	}

	@Override
	public V get(K key) {
		integerkey = Math.abs(key.hashCode() % mod);

		for (int i = 0; i < (1 << size); i++) {
			index = (integerkey + (i * (i + 1) / 2)) % mod;
			if (!freePlaces[index])
				break;
			if (quad[index].key.equals(key))
				return quad[index].value;
		}
		return null;
	}

	@Override
	public void delete(K key) {

		int integerkey = Math.abs(key.hashCode() % mod);
		for (int i = 0; i < 1 << size; i++) {
			index = (integerkey % mod + (i * (i + 1) / 2) % mod) % mod;
			if (!freePlaces[index])
				break;
			if (quad[index].key.equals(key)) {
				numberOfElement--;
				freePlaces[index] = false;
				break;
			}
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
			if (freePlaces[i])
				ilterator.add(quad[i].key);

		}
		return ilterator;
	}

}
