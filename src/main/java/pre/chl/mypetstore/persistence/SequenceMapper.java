package pre.chl.mypetstore.persistence;

import pre.chl.mypetstore.domain.Sequence;

public interface SequenceMapper {
    Sequence getSequence(String sequencename);
    void updateSequence(Sequence sequence);
}
