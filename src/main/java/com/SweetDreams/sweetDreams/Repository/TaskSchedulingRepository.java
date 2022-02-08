package com.SweetDreams.sweetDreams.Repository;

import com.SweetDreams.sweetDreams.Models.Cupom;
import com.SweetDreams.sweetDreams.Models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskSchedulingRepository  extends MongoRepository<Task, String> {
}
