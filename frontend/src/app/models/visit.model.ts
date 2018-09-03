import {User} from './user.model';
import {VisitModel} from './visitModel.model';

export class Visit {

  idVisit?: number;
  term?: string;
  status?: string;
  visitModel?: VisitModel;
  user?: User;

}

