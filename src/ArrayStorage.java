import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int countResume;

    void clear() {
        Arrays.fill(storage, 0, countResume, null);
        countResume = 0;
    }

    void save(Resume resume) {
        if (countResume < 10000) {
            storage[countResume++] = resume;
        } else {
            System.out.println("Хранилище переполнено");
        }
    }

    Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.printf("резюме с идентификатором %s отсутствует в хранилище.\n", uuid);
        return null;
    }

    void delete(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            System.arraycopy(storage, index + 1, storage, index, --countResume - index);
            storage[countResume] = null;
            System.out.printf("резюме с идентификатором %s удалено из хранилища.\n", uuid);
        } else {
            System.out.printf("резюме с идентификатором %s отсутствует в хранилище.\n", uuid);
        }

    }

    private int findIndex(String uuid) {
        int index = 0;
        for (Resume resume : getAll()) {
            if (resume.uuid.equals(uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, countResume);
    }

    int size() {
        return countResume;
    }
}
