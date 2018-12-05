package com.quantdo.market.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public final class GlobalObj {
//	Hashtable与HashMap另一个区别是HashMap的迭代器（Iterator）是fail-fast迭代器，而Hashtable的enumerator迭代器不是fail-fast的。所以当有其它线程改变了HashMap的结构（增加或者移除元素），将会抛出ConcurrentModificationException，但迭代器本身的remove()方法移除元素则不会抛出ConcurrentModificationException异常。但这并不是一个一定发生的行为，要看JVM。
//	ConcurrentHashMap提供了与Hashtable和SynchronizedMap不同的锁机制。Hashtable中采用的锁机制是一次锁住整个hash表，从而在同一时刻只能由一个线程对其进行操作；而ConcurrentHashMap中则是一次锁住一个桶。
//
//	ConcurrentHashMap默认将hash表分为16个桶，诸如get、put、remove等常用操作只锁住当前需要用到的桶。这样，原来只能一个线程进入，现在却能同时有16个写线程执行，并发性能的提升是显而易见的。
	
		//HashMap可以接受为null的键值(key)和值(value)，而Hashtable则不行,HashMap不能保证随着时间的推移Map中的元素次序是不变的。HashMap中不能由get()方法来判断HashMap中是否存在某个key，应该用containsKey()方法来判断
		public static final HashMap<String, String> HASHMAP = new HashMap<String, String>();
	
		//Hashtable是线程安全的，多个线程可以共享一个Hashtable,Hashtable是线程安全的也是synchronized，所以在单线程环境下它比HashMap要慢。
		public static final Hashtable<String,String> HASHTABLE = new Hashtable<String,String>();
		

		//Java 5提供了ConcurrentHashMap，它是HashTable的替代，比HashTable的扩展性更好。并发性能比HashTable提升16倍有些方法需要跨段，比如size()和containsValue()，它们可能需要锁定整个表而而不仅仅是某个段，这需要按顺序锁定所有段，操作完毕后，又按顺序释放所有段的锁
		public static final Map<String,String> MAP = new ConcurrentHashMap<String,String>();

//		是一个适用于高并发场景下的队列，通过无锁的方式，实现了高并发状态下的高性能，通常ConcurrentLikedQueue性能好于BlockingQueue。
//		ConcurrentLinkedQueue的API.size() 是要遍历一遍集合的，速很慢，所以判空时，尽量要避免用size()，而改用isEmpty()。
//		它是一个基于连接节点的无界线程安全队列。该队列的元素遵循先进先出的原则。头是最先加入的，尾是最近加入的，该队列不允许null元素。

//		ConcurrentLinkedQueue重要方法：
//
//		add()和offer()都是加入元素的方法(在ConcurrentLinkedQueue中，这两个方法没有任何区别)。
//
//		poll()和peek()都是取头元素节点，区别在于前者会删除元素，后者不会。
		
		public static final Queue<String> CQUEUE = new ConcurrentLinkedQueue<String>();
		
//		基于数组的阻塞队列实现，在ArrayBlockingQueue内部，维护了一个定长数组，以便缓存队列中的数据对象，其内部没实现读写分离，也就意味着生产和消费不能完全并行，长度是需要定义的，可以指定先进先出或者先进后出，也叫有界队列，在很多场合非常适合使用。	
		public static final Queue<String> AQUEUE = new ArrayBlockingQueue<String>(10);

//		1、ArrayList是线程不安全的；
//		2、Vector是比较古老的线程安全的，但性能不行；
//		3、CopyOnWriteArrayList在兼顾了线程安全的同时，又提高了并发性，性能比Vector有不少提高
		
		public static final List<Object> LIST = new ArrayList<Object>();
		
		public static final List<Object> VECTOR = new Vector<Object>();
		
		public static final List<Object> RiskExpIdxList = new CopyOnWriteArrayList<Object>();
		
		public static HashMap<String, String> getHashmap() {
			return HASHMAP;
		}

		public static Hashtable<String, String> getHashtable() {
			return HASHTABLE;
		}

		public static Map<String, String> getMap() {
			return MAP;
		}

		public static Queue<String> getCqueue() {
			return CQUEUE;
		}

		public static Queue<String> getAqueue() {
			return AQUEUE;
		}

		public static List<Object> getList() {
			return LIST;
		}

		public static List<Object> getVector() {
			return VECTOR;
		}

		public static List<Object> getRiskexpidxlist() {
			return RiskExpIdxList;
		}
		
}
