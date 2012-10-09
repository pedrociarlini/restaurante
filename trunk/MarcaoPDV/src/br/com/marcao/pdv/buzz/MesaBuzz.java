package br.com.marcao.pdv.buzz;


import pedrociarlini.reuse.buzz.MainBuzz;
import br.com.marcao.pdv.dao.MesaDAO;
import br.com.marcao.pdv.entity.MesaEntity;

public class MesaBuzz extends MainBuzz<MesaEntity> {

    private MesaDAO mesaDao;

    MesaBuzz(Class<MesaEntity> classe) {
        super(classe);
    }

    @Override
    protected MesaDAO getMainDao() {
        if (mesaDao == null) {
            mesaDao = new MesaDAO();
        }
        return mesaDao;
    }
}
