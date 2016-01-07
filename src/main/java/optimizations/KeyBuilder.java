package optimizations;

public class KeyBuilder {
    private StringBuilder keyParts = new StringBuilder();

    public KeyBuilder appendKeyPart(int keyPart) {
        return appendKeyPart("" + keyPart);
    }

    public KeyBuilder appendKeyPart(String strKeyPart) {
        addKeyPartSeparatorIfNeeded();
        keyParts.append(strKeyPart);
        return this;
    }

    private void addKeyPartSeparatorIfNeeded() {
        if (keyParts.length() > 0) {
            keyParts.append(":");
        }
    }

    public String makeKey() {
        return keyParts.toString();
    }
}