import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

class MyLinkedListTest {

  private MyLinkedList<Integer> list;

  @BeforeEach
  void setUp() {
    list = new MyLinkedList<>();
  }

  @Test
  void testSizeEmptyList() {
    assertEquals(0, list.size());
  }

  @Test
  void testAddFirstAndSize() {
    list.addFirst(10);
    assertEquals(1, list.size());
    assertEquals(10, list.getFirst());

    list.addFirst(20);
    assertEquals(2, list.size());
    assertEquals(20, list.getFirst());
  }

  @Test
  void testAddLastAndSize() {
    list.addLast(10);
    assertEquals(1, list.size());
    assertEquals(10, list.getLast());
    list.addLast(20);
    assertEquals(2, list.size());
    assertEquals(20, list.getLast());
  }

  @Test
  void testAddAtIndex() {
    list.add(0, 10);
    list.add(1, 30);
    list.add(1, 20);
    assertEquals(3, list.size());
    assertEquals(10, list.get(0));
    assertEquals(20, list.get(1));
    assertEquals(30, list.get(2));
  }

  @Test
  void testAddAtIndexInvalid() {
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 10));
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, 10));
  }

  @Test
  void testGetFirstEmptyList() {
    assertThrows(NoSuchElementException.class, () -> list.getFirst());
  }

  @Test
  void testGetLastEmptyList() {
    assertThrows(NoSuchElementException.class, () -> list.getLast());
  }

  @Test
  void testGetFirstAndLast() {
    list.addFirst(10);
    list.addLast(20);
    list.addLast(30);
    assertEquals(10, list.getFirst());
    assertEquals(30, list.getLast());
  }

  @Test
  void testGetByIndex() {
    list.addLast(10);
    list.addLast(20);
    list.addLast(30);
    assertEquals(10, list.get(0));
    assertEquals(20, list.get(1));
    assertEquals(30, list.get(2));
  }

  @Test
  void testGetByIndexInvalid() {
    list.addLast(10);
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
  }

  @Test
  void testRemoveFirstEmptyList() {
    assertThrows(NoSuchElementException.class, () -> list.removeFirst());
  }

  @Test
  void testRemoveFirst() {
    list.addLast(10);
    list.addLast(20);
    list.addLast(30);
    assertEquals(10, list.removeFirst());
    assertEquals(2, list.size());
    assertEquals(20, list.getFirst());
    assertEquals(20, list.removeFirst());
    assertEquals(1, list.size());
    assertEquals(30, list.getFirst());
  }

  @Test
  void testRemoveLastEmptyList() {
    assertThrows(NoSuchElementException.class, () -> list.removeLast());
  }

  @Test
  void testRemoveLast() {
    list.addLast(10);
    list.addLast(20);
    list.addLast(30);
    assertEquals(30, list.removeLast());
    assertEquals(2, list.size());
    assertEquals(20, list.getLast());
    assertEquals(20, list.removeLast());
    assertEquals(1, list.size());
    assertEquals(10, list.getLast());
  }

  @Test
  void testRemoveByIndex() {
    list.addLast(10);
    list.addLast(20);
    list.addLast(30);
    list.addLast(40);
    assertEquals(20, list.remove(1));
    assertEquals(3, list.size());
    assertEquals(30, list.get(1));
    assertEquals(10, list.remove(0));
    assertEquals(2, list.size());
    assertEquals(30, list.getFirst());
    assertEquals(40, list.remove(1));
    assertEquals(1, list.size());
    assertEquals(30, list.getLast());
  }

  @Test
  void testRemoveByIndexInvalid() {
    list.addLast(10);
    assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
    assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
  }

  @Test
  void testComplexOperations() {
    list.addFirst(20);
    list.addLast(30);
    list.add(1, 25);
    list.addFirst(10);
    list.addLast(40);
    assertEquals(5, list.size());
    assertEquals(10, list.getFirst());
    assertEquals(40, list.getLast());
    assertEquals(10, list.removeFirst());
    assertEquals(40, list.removeLast());
    assertEquals(25, list.remove(1));
    assertEquals(2, list.size());
    assertEquals(20, list.getFirst());
    assertEquals(30, list.getLast());
  }

  @Test
  void testSingleElementOperations() {
    list.addFirst(10);
    assertEquals(1, list.size());
    assertEquals(10, list.getFirst());
    assertEquals(10, list.getLast());
    assertEquals(10, list.get(0));
    assertEquals(10, list.removeFirst());
    assertEquals(0, list.size());
    list.addLast(20);
    assertEquals(1, list.size());
    assertEquals(20, list.removeLast());
    assertEquals(0, list.size());
  }
}