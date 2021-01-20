import { IProject } from 'app/shared/model/project.model';
import { IUser } from 'app/core/user/user.model';
import { IWeek } from 'app/shared/model/week.model';

export interface IActivity {
  id?: number;
  timeSpent?: number;
  project?: IProject;
  user?: IUser;
  week?: IWeek;
}

export class Activity implements IActivity {
  constructor(public id?: number, public timeSpent?: number, public project?: IProject, public user?: IUser, public week?: IWeek) {}
}
