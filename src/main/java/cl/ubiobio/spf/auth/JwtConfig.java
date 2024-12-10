package cl.ubiobio.spf.auth;

public class JwtConfig {
    public static final String LLAVE_SECRETA = "alguna.clave.secreta.12345678";


    public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEogIBAAKCAQEAttNwTZ/EXt6IJe/pXWpWxoR+WP1kZtn8WeVfv+LSFGc++uib\n" +
            "4KwLvNP29RRxdtjhfXYnXpBhrcLXYoMR+vT9ecfHclDpkuQKAKhQqPlIyG5UzPgv\n" +
            "DOJBAOHemVhSMpBcv9Ahr8aEzNjmIsy9S1Nqyxs329n5eXXsCOMaj24MrjatWhor\n" +
            "IwNPeJR3EUZLwfFeH/LsDAcnan1JwwlcRzQBWTJfYWmp2V3zSwkVs4u+FXW6c9Iy\n" +
            "rYdXLzX5cLTJPyAWXGmeLvBAA8rRaa5hHMNGna6qrqbeGcSxBQiBkhGG579rKle4\n" +
            "n2bocIDvKR4VAlTsAnN/v0QGVhiT1H1YRIRIVwIDAQABAoIBAA29M/pRYFN0y6x2\n" +
            "LAZeIGlIuan8WNg113X1+80SwZDQ9XWrl1M5+qPVFgDy6VfyEnf4Mhs0JHMHamI3\n" +
            "0VkXohjNv5pCpFrqkincfDtGiIava+X/XKHOOu5TCwRhE8RC/wVXIcDw3Tf7sMe6\n" +
            "tRr9CNB8miUGa24KQ5OjZXKT5ZYN21HiZckDXAV0H3kgiBzsbX+TWZCqYh/L8P6d\n" +
            "dCEa58Ob17BOdeHzYN0wHXTHiIaXavaArhzadiv445alvf1beamqgsNzG58+np6j\n" +
            "moeo7JmNZdiV5KK1i3O6zZ/k+C5f+JQj2GD8ABGnqg17kcurp3Jk3wOswgWp5rlQ\n" +
            "++i3INkCgYEA3Ak/RnqcVvpurW1hSw6qB6TrQPPNzlRbb/SScI/g+9END14aLskD\n" +
            "1BPmG4oJLHTsn2alOH80mEp4RgO2LAXls+IzxYUeMUiJ/9vdqUqM7LKKvidjj4Kt\n" +
            "Dt13LszMqjc9Kn8ZhVMZcZSLjRpIInXfJk/dl6jc79yJVYccZYBVw0MCgYEA1LU+\n" +
            "Fm7aanElKu7mp1LUBmE0NSbijYZNDol21IEOQl+R70VecOgN4Jo2BS/0wJw5ZFjT\n" +
            "H9an1ctEjx21V3YtE6s32em11C8advCd+iIvefcYhQmkPNV1ZXrgutmCnCovc40f\n" +
            "69W3mqOgXAVKhgCpzD6aMx4o1PVhELRq6qxSM10CgYB2GQR8KHzW9i0HKkjpByLd\n" +
            "XXfi8bhJgN9e1I7wmViw74Ap+mDlMYC7iD6eE1VfJhaGa4pMK4IoJvgs4/sX38Zs\n" +
            "6QDOh+cvnnZq/DAZl+jRdfafnvaB5SpLazR3yvYieel7cKJa+8IRQyAviKak0E0H\n" +
            "/B+8Mab7moufexgTpnDhjwKBgCwkxRLgxKFsg9sqI2I8VVZRnAUf4cqfQaSvsKKf\n" +
            "efcaYG34xoXGEwM23dkrv66tfVeu+3nTEZfUDJFrB/RzyR0Y3olx8FNtxx9CBncf\n" +
            "2xyDxGnvu08Iuclqu/LrKyj/tENV2XcXLniAVspiKrourw59O4E6VB0GI7PUzY+Q\n" +
            "XardAoGADFMn6mQB6HMRYdbZMQjz7Ukbwkk/hoNfXteyO6/Dk7t+8HmybVemYIW6\n" +
            "MOG3jRGf0Guk/aEP7taaGU5pLNDGFJ/2mz+qBQdPQo0VFUhbeiDKW7apobFDUY3T\n" +
            "hzs03VGW9GVt6LIDOsEd8fCtWIKqWffhYQIWDk0bYNh1Eo8p30c=\n" +
            "-----END RSA PRIVATE KEY-----\n";

    public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAttNwTZ/EXt6IJe/pXWpW\n" +
            "xoR+WP1kZtn8WeVfv+LSFGc++uib4KwLvNP29RRxdtjhfXYnXpBhrcLXYoMR+vT9\n" +
            "ecfHclDpkuQKAKhQqPlIyG5UzPgvDOJBAOHemVhSMpBcv9Ahr8aEzNjmIsy9S1Nq\n" +
            "yxs329n5eXXsCOMaj24MrjatWhorIwNPeJR3EUZLwfFeH/LsDAcnan1JwwlcRzQB\n" +
            "WTJfYWmp2V3zSwkVs4u+FXW6c9IyrYdXLzX5cLTJPyAWXGmeLvBAA8rRaa5hHMNG\n" +
            "na6qrqbeGcSxBQiBkhGG579rKle4n2bocIDvKR4VAlTsAnN/v0QGVhiT1H1YRIRI\n" +
            "VwIDAQAB\n" +
            "-----END PUBLIC KEY-----\n";
}