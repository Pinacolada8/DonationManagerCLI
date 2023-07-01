package Infrastructure;

import domain.models.entities.ItemDonationRequest;
import domain.services.contracts.IItemDonationRequestRepository;

import java.io.IOException;

public class FileSystemItemDonationRequestRepository extends FileSystemRepository<ItemDonationRequest> implements IItemDonationRequestRepository {

    public FileSystemItemDonationRequestRepository(String dbFolderPath, String dbFilename) throws IOException {
        super(dbFolderPath, dbFilename);
    }
}
