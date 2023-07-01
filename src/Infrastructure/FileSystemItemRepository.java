package Infrastructure;

import domain.models.entities.ItemEntry;
import domain.services.contracts.IItemRepository;

import java.io.IOException;
import java.util.Objects;

public class FileSystemItemRepository extends FileSystemRepository<ItemEntry> implements IItemRepository {

    public FileSystemItemRepository(String dbFolderPath, String dbFilename) throws IOException {
        super(dbFolderPath, dbFilename);
    }

    @Override
    public ItemEntry findByName(String name) {
        return findFirst(x -> Objects.equals(x.getName(), name));
    }
}
