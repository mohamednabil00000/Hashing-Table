package HashingTechnique;

import java.util.ArrayList;

import Interfaing.HashTable;

public class SeparateChaining<K, V> implements HashTable<K, V> {

	private ArrayList<Pair<K, V>> openHash[];
	private int howManyElement;
	private int integerkey;
	private int mod;
	private ArrayList<K> ilterator;

	public SeparateChaining() {
		mod = 10;
		openHash = new ArrayList[10];
		for (int i = 0; i < 10; i++) {
			openHash[i] = new ArrayList();
		}
		howManyElement = 0;
	}
	public int sizeOfArray() {
		return mod;
	}

	public void rehashing() {
		ArrayList<Pair<K, V>> temp[] = new ArrayList[mod];

		for (int i = 0; i < mod; i++) {
			temp[i] = new ArrayList();
			for (int j = 0; j < openHash[i].size(); j++) {
				temp[i].add(openHash[i].get(j));
			}
		}

		mod *= 2;
		openHash = new ArrayList[mod];
		for (int i = 0; i < mod; i++) {
			openHash[i] = new ArrayList();
		}
		for (int i = 0; i < mod / 2; i++) {
			for (int j = 0; j < temp[i].size(); j++) {
				integerkey = Math.abs(temp[i].get(j).key.hashCode() % mod);
				openHash[integerkey].add(temp[i].get(j));
			}
		}
	}

	@Override
	public void put(K key, V value) {
		delete(key);
		integerkey = Math.abs(key.hashCode() % mod);
		openHash[integerkey].add(new Pair(key, value));
		howManyElement++;
		if (howManyElement / mod >= 3) {
			rehashing();
		}

	}

	@Override
	public V get(K key) {
		integerkey = Math.abs(key.hashCode() % mod);
		for (int i = 0; i < openHash[integerkey].size(); i++) {
			if (openHash[integerkey].get(i).key.equals(key)) {
				return openHash[integerkey].get(i).value;
			}
		}
		return null;
	}

	@Override
	public void delete(K key) {
		integerkey = Math.abs(key.hashCode() % mod);
		for (int i = 0; i < openHash[integerkey].size(); i++) {
			if (openHash[integerkey].get(i).key.equals(key)) {
				openHash[integerkey].remove(i);
				howManyElement--;
				break;
			}
		}
		return;

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
		if (howManyElement == 0)
			return true;
		return false;
	}

	@Override
	public int size() {
		return howManyElement;
	}

	@Override
	public Iterable<K> keys() {
		ilterator = new ArrayList();
		for (int i = 0; i < openHash.length; i++) {
			for (int j = 0; j < openHash[i].size(); j++) {
				ilterator.add(openHash[i].get(j).key);
			}
		}
		return ilterator;
	}

}
