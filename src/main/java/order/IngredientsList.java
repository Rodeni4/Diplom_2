package order;


import java.util.List;

public class IngredientsList {
    public static List<String> defaultList() {
        return List.of("61c0c5a71d1f82001bdaaa6d",
                "61c0c5a71d1f82001bdaaa6f",
                "61c0c5a71d1f82001bdaaa70",
                "61c0c5a71d1f82001bdaaa71",
                "61c0c5a71d1f82001bdaaa72",
                "61c0c5a71d1f82001bdaaa6e",
                "61c0c5a71d1f82001bdaaa73",
                "61c0c5a71d1f82001bdaaa74",
                "61c0c5a71d1f82001bdaaa6c",
                "61c0c5a71d1f82001bdaaa75",
                "61c0c5a71d1f82001bdaaa76",
                "61c0c5a71d1f82001bdaaa77",
                "61c0c5a71d1f82001bdaaa78",
                "61c0c5a71d1f82001bdaaa79",
                "61c0c5a71d1f82001bdaaa7a");
    }

    public static List<String> fluorescentImmortalBurgerList() {
        return List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
    }

    public static List<String> emptyList() {
       return null;
    }

    public static List<String> withIncorrectHashIngredientsList() {
        return List.of("000000000000000000000000", "111111111111111111111111");
    }
}
