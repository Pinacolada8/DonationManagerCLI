package Infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import domain.models.entities.BaseEntity;
import domain.services.contracts.IRepository;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FileSystemRepository<T extends BaseEntity> implements IRepository<T> {
    @SuppressWarnings("unchecked")
    protected Class<T> entityTypeClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];

    protected ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    protected String dbFolderPath;
    protected String dbFilename;
    protected File dbFile;

    public FileSystemRepository(String dbFolderPath, String dbFilename) throws IOException {

        this.dbFolderPath = dbFolderPath;
        this.dbFilename = dbFilename;

        this.dbFile = new File(this.dbFolderPath, this.dbFilename);
        this.dbFile.getParentFile().mkdirs();

        if (this.dbFile.createNewFile())
            objectMapper.writeValue(this.dbFile, new ArrayList<>());

        if (!(this.dbFile.isFile() && this.dbFile.canRead() && this.dbFile.canWrite()))
            throw new IOException("Error with DB file: " + this.dbFile.getPath());
    }

    // ====================================================================================== //

    @Override
    public List<T> getAll() {
        var typeFactory = objectMapper.getTypeFactory();
        var javaType = typeFactory.constructParametricType(List.class, this.entityTypeClass);
        try {

            List<T> values = objectMapper.readValue(this.dbFile, javaType);
            if (values == null)
                values = new ArrayList<>();
            return values;
        } catch (IOException e) {
            System.err.printf("||DB:%s||Error reading values", this.dbFilename);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> find(Predicate<? super T> predicate) {
        var dbValues = getAll();
        return dbValues.stream().filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public T findFirst(Predicate<? super T> predicate) {
        return find(predicate).stream().findFirst().orElse(null);
    }

    @Override
    public T find(Integer id) {
        return findFirst(x -> Objects.equals(x.getId(), id));
    }

    // ====================================================================================== //

    protected boolean save(List<T> entities) {
        try {
            objectMapper.writeValue(this.dbFile, entities);
            return true;
        } catch (IOException e) {
            System.err.printf("||DB:%s||Error saving values", this.dbFilename);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public T add(T entity) {
        var dbValues = getAll();

        if (entity.getId() != null && entity.getId() != 0)
            return null;

        var newId = dbValues.stream()
                .map(BaseEntity::getId)
                .max(Comparator.comparingInt(a -> a))
                .orElse(0) + 1;

        entity.setId(newId);
        dbValues.add(entity);
        save(dbValues);
        return entity;
    }

    @Override
    public boolean remove(T entity) {
        var dbValues = getAll();

        if (find(entity.getId()) == null)
            return false;

        dbValues = dbValues.stream().filter(x -> !x.getId().equals(entity.getId())).collect(Collectors.toList());
        return save(dbValues);
    }

    @Override
    public T update(T entity) {
        var dbValues = getAll();
        var indexOfValue = dbValues.indexOf(entity);

        if (indexOfValue < 0 || dbValues.get(indexOfValue).getId() != entity.getId())
            return null;

        dbValues.set(indexOfValue, entity);
        return entity;
    }

    // ====================================================================================== //

}
