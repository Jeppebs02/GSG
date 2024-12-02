package dal;

import model.Shift;

public interface ShiftDBIF {

	Shift saveShift(Shift shift) throws Exception;
}
