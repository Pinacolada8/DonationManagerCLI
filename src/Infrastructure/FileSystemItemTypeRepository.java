package Infrastructure;

import domain.models.entities.ItemType;
import domain.services.contracts.IItemTypeRepository;

import java.io.IOException;
import java.util.Objects;

public class FileSystemItemTypeRepository extends FileSystemRepository<ItemType> implements IItemTypeRepository {

    public FileSystemItemTypeRepository(String dbFolderPath, String dbFilename) throws IOException {
        super(dbFolderPath, dbFilename);
    }

    @Override
    public ItemType findByName(String name) {
        return findFirst(x -> Objects.equals(x.getName(), name));
    }

    @Override
    public ItemType add(String name) {
        return add(new ItemType(name));
    }
}
