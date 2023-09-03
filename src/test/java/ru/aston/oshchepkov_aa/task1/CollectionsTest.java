package ru.aston.oshchepkov_aa.task1;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CollectionsTest {
    private static final List<Integer> defaultList = List.of(1, 2, 3);
    private static final Map<String, Integer> defaultMap = Map.of("a", 0, "b", 1, "c", 2);
    private static final TreeSet<String> defaultSet = new TreeSet<>(Comparator.comparing(String::toLowerCase));
    @Test
    void test_array_list_creation() {
        var list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        var copyList = new ArrayList<>(list);

        var capacity = 10;
        var anotherNewList = new ArrayList<>(capacity);
        anotherNewList.addAll(list);

        assertThat(list)
                .isEqualTo(defaultList);
        assertThat(copyList)
                .isEqualTo(defaultList);

        assertThat(anotherNewList)
                .isEqualTo(defaultList);
    }

    @Test
    void test_create_immutable_list() {
        var immutableList = List.of(1, 2, 3);

        assertThatThrownBy(() -> immutableList.remove(2))
                .as("throws exception")
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void test_list_contains() {
        assertThat(defaultList.contains(1))
                .isTrue();

        assertThat(defaultList.contains(0))
                .isFalse();
    }

    @Test
    void test_map_creation() {
        var map = new HashMap<String, Integer>();
        map.put("a", 0);
        map.put("b", 1);
        map.put("c", 2);

        var copyMap = new HashMap<>(defaultMap);

        var cap = 32;
        var loadFactor = 1f;

        var anotherMap = new HashMap<>(cap, loadFactor);
        var anotherAnotherMap = new HashMap<>(cap);

        assertThat(map)
                .isEqualTo(defaultMap);

        assertThat(copyMap)
                .isEqualTo(defaultMap);
    }

    @Test
    void test_map_contains() {
        assertThat(defaultMap.containsKey("a"))
                .isTrue();
        assertThat(defaultMap.containsKey("aa"))
                .isFalse();

        assertThat(defaultMap.containsValue(0))
                .isTrue();
    }

    @Test
    void test_tree_set_creatioon() {
        Comparator<String> comp = Comparator.comparing(String::toLowerCase);
        var ts = new TreeSet<String>(comp);

        var noCompSet = new TreeSet<>();
        var cc = new TreeSet<>(noCompSet);
    }

    @Test
    void test_set_add() {
        defaultSet.add("Z");
        defaultSet.add("A");

        assertThat(defaultSet)
                .hasSize(2);

        defaultSet.clear();
    }
}
