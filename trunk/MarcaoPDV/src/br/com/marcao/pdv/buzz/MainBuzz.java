package br.com.marcao.pdv.buzz;

import java.util.List;

import br.com.marcao.pdv.dao.MainDAO;
import br.com.marcao.pdv.entity.MesaEntity;

public abstract class MainBuzz<T> {

    protected abstract MainDAO getMainDao();

    public List<MesaEntity> retrieveAll() {
    
        List<MesaEntity> result = getMainDao().getAll(MesaEntity.class);
        
        return result;
    }
}
